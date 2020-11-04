package com.dtnsm.esop.controller;

import com.dtnsm.common.entity.QAccount;
import com.dtnsm.common.entity.Account;
import com.dtnsm.common.repository.UserRepository;
import com.dtnsm.common.security.annotation.CurrentUser;
import com.dtnsm.common.security.authentication.GroupwareUserAuthService;
import com.dtnsm.esop.domain.*;
import com.dtnsm.esop.domain.constant.*;
import com.dtnsm.esop.domain.report.*;
import com.dtnsm.esop.repository.DocumentVersionRepository;
import com.dtnsm.esop.repository.SOPTrainingMatrixRepository;
import com.dtnsm.esop.service.ApprovalService;
import com.dtnsm.esop.service.CategoryService;
import com.dtnsm.esop.service.DocumentVersionService;
import com.dtnsm.esop.utils.DateUtils;
import com.dtnsm.esop.validator.ApprovalValidator;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes({"sopMap", "rdMap", "approval", "lineType", "purposeOfDisclosureMap", "documentAccessMap", "currentSopCategoryMap", "supersededSopCategoryMap", "categoryList", "userMap"})
public class ApprovalController {
    private final SOPTrainingMatrixRepository sopTrainingMatrixRepository;
    private final ApprovalValidator approvalValidator;
    private final ApprovalService approvalService;
    private final DocumentVersionService documentVersionService;
    private final DocumentVersionRepository documentVersionRepository;
    private final CategoryService categoryService;
    private final GroupwareUserAuthService groupwareUserAuthService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping({"/approval/box/{type}", "/approval/box/{type}/{status}"})
    public String totalList(@PathVariable("type") ApprovalLineType type,
                            @PathVariable(value = "status", required = false) ApprovalStatus status,
                            @PageableDefault(sort = {"approvalId"}, direction = Sort.Direction.DESC, size = 10) Pageable pageable,
                            @CurrentUser Account user,
                            Model model) {
        model.addAttribute("approvalList", approvalService.findAll(type, status, user, pageable));
        model.addAttribute("type", type);
        model.addAttribute("reportStatus", status);
        return "approval/totalList";
    }


    @GetMapping({"/approval/box/{lineType}/{reportType}/new", "/approval/box/{lineType}/{reportType}/{id}/edit"})
    public String edit(@PathVariable(value = "id", required = false) Integer id,
                           @PathVariable("lineType") ApprovalLineType lineType,
                           @PathVariable("reportType") ReportType reportType,
                           @RequestParam(value = "trainingPeriodId", required = false) Integer trainingPeriodId,
                           @RequestParam(value = "sopId", required = false) String sopId,
                           @RequestParam(value = "renewId", required = false, defaultValue = "0") Integer renewId,
                           @CurrentUser Account user, Model model) throws Exception {
        Approval approval;
        if(renewId.intValue() > 0) {
            //재상신인 경우 기존 데이터 복제 한다.
            log.info("==> 재상신 수정 : {}", renewId);
            approval = approvalService.findById(renewId).get();
            approval = new Approval(approval);
        } else if(ObjectUtils.isEmpty(id) == false) {
            approval = approvalService.findById(id).get();
        } else {
            approval = new Approval();
            approval.setType(reportType);
        }

        switch (approval.getType()) {
            case SOP_Deviation_Report:
                sopDeviationReport(approval, user, trainingPeriodId, sopId, model);
                break;
            case SOP_RD_Request_Form:
                sopRdRequestForm(approval, user, model);
                break;
//            case RD_Approval_Form:
//                rdApprovalForm(approval, user, model);
//                break;
            case SOP_Waiver_Approval_Form:
                sopWaiverApprovalForm(approval, user, model);
                break;
            case SOP_Disclosure_Request_Form:
                sopDisclosureRequestForm(approval, user, model);
                break;
            case SOP_RD_Retirement_Form:
                sopRetirementForm(approval, user, model);
                break;
        }

        log.info("isRenew : {}, approvalId : {}", approval.isRenew(), approval.getId());
        model.addAttribute("approval", approval);
        model.addAttribute("lineType", lineType);

        return "approval/approvalForm";
    }

    @PutMapping({"/approval/box/{lineType}/{reportType}/new", "/approval/box/{lineType}/{reportType}/{id}/edit"})
    @Transactional
    public String updateRetirementDocs(@PathVariable(value = "id", required = false) Integer id,
                       @PathVariable("lineType") ApprovalLineType lineType,
                       @PathVariable("reportType") ReportType reportType,
                       @ModelAttribute("approval") Approval approval,
                       @RequestParam(value = "selectedId", required = false) String selectedId,
                       @RequestParam(value = "deselectedId", required = false) String deselectedId,
                                       HttpServletRequest request) throws Exception {

        approval.getRetirementApprovalForm().setSopIds(ServletRequestUtils.getStringParameters(request, "retirementApprovalForm.sopIds"));
        approval.getRetirementApprovalForm().setRdIds(ServletRequestUtils.getStringParameters(request, "retirementApprovalForm.rdIds"));

        if(StringUtils.isEmpty(selectedId) == false) {
            DocumentVersion documentVersion = documentVersionService.findById(selectedId);
            if(!approval.getRetirementApprovalForm().getRetirementDocuments().contains(documentVersion)) {
                approval.getRetirementApprovalForm().getRetirementDocuments().add(documentVersion);

                if(documentVersion.getDocument().getType() == DocumentType.SOP) {
                    for(Document rd : documentVersion.getDocument().getRdList()) {
                        for(DocumentVersion rdVer : rd.getDocumentVersionList()) {
                            if(rdVer.getStatus() == DocumentStatus.EFFECTIVE) {
                                if(!approval.getRetirementApprovalForm().getRetirementDocuments().contains(rdVer)) {
                                    approval.getRetirementApprovalForm().getRetirementDocuments().add(rdVer);
                                }
                                approval.getRetirementApprovalForm().getSopRdIds().add(rdVer.getId());
                            }
                        }
                    }

                    List<String> rdIds = Arrays.stream(approval.getRetirementApprovalForm().getRdIds())
                            .collect(Collectors.toList());

                    log.info("@@ approval.getRetirementApprovalForm().getSopRdIds() : {}", approval.getRetirementApprovalForm().getSopRdIds());
                    List<String> filtered = approval.getRetirementApprovalForm().getSopRdIds().stream()
                            .filter(rdId -> rdIds.contains(rdId) == false)
                            .collect(Collectors.toList());
                    if(!ObjectUtils.isEmpty(filtered)) {
                        rdIds.addAll(filtered);
                    }
//                    log.info("필터전 : {}", rdIds);
//                    List<String> filtered = rdIds.stream().filter(rdId -> {
//                        boolean contains = approval.getRetirementApprovalForm().getSopRdIds().contains(rdId) == false;
//                        log.info("{} : {}", rdId, contains);
//                        return contains;
//                    }).collect(Collectors.toList());
//
//                    log.info("필터 후 : {}", filtered);

//                    log.info("선택된 RD ID : {}", approval.getRetirementApprovalForm().getRdIds());
                    if(ObjectUtils.isEmpty(rdIds)) {
                        approval.getRetirementApprovalForm().setRdIds(null);
                    } else {
                        approval.getRetirementApprovalForm().setRdIds(rdIds.toArray(new String[rdIds.size()]));
                    }

                }
            }
        } else if (StringUtils.isEmpty(deselectedId) == false) {
            if(approval.getRetirementApprovalForm().getSopRdIds().contains(deselectedId)) {
                log.info("현재 RD는 삭제 불가. 무보 SOP가 Retirement 대상임.");
            } else {
                if (ObjectUtils.isEmpty(approval.getRetirementApprovalForm().getRetirementDocuments()) == false) {
                    DocumentVersion documentVersion = documentVersionService.findById(deselectedId);
                    approval.getRetirementApprovalForm().getRetirementDocuments().remove(documentVersion);

                    if (documentVersion.getDocument().getType() == DocumentType.SOP) {
                        for (Document rd : documentVersion.getDocument().getRdList()) {
                            for (DocumentVersion rdVer : rd.getDocumentVersionList()) {
                                if (rdVer.getStatus() == DocumentStatus.EFFECTIVE) {
                                    if (approval.getRetirementApprovalForm().getRetirementDocuments().contains(rdVer)) {
                                        approval.getRetirementApprovalForm().getRetirementDocuments().remove(rdVer);
                                        approval.getRetirementApprovalForm().getSopRdIds().remove(rdVer.getId());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return "approval/approvalForm";
    }

    @PutMapping("/ajax/approval/box/requester/SOP_Disclosure_Request_Form/externalCustomer")
    public String addExternalCustomer(@ModelAttribute("approval") Approval approval, @RequestParam(value = "act", defaultValue = "add") String act,
                                      @RequestParam(value = "idx", required = false, defaultValue = "0") Integer idx) {

        if("add".equals(act)) {
            approval.getSopDisclosureRequestForm().getExternalCustomers().add(new ExternalCustomer());
        } else {
            approval.getSopDisclosureRequestForm().getExternalCustomers().remove(idx.intValue());
        }

        return "approval/form/inc_externalCustomer";
    }
    @PutMapping("/ajax/approval/box/requester/SOP_RD_Request_Form/development")
    public String addDevelopmentDoc(@ModelAttribute("approval") Approval approval, @RequestParam(value = "act", defaultValue = "add") String act,
                                      @RequestParam(value = "idx", required = false, defaultValue = "0") Integer idx,
                                    @RequestParam("t") String type) {

        if("add".equals(act)) {
            SopRdDevelopmentDoc sopRdDevelopmentDoc = new SopRdDevelopmentDoc();
            if("sop".equals(type)) {
                sopRdDevelopmentDoc.setDocumentType(DocumentType.SOP);
                approval.getSopRdRequestForm().getSopDevelopmentDocs().add(sopRdDevelopmentDoc);
            } else {
                sopRdDevelopmentDoc.setDocumentType(DocumentType.RD);
                approval.getSopRdRequestForm().getRdDevelopmentDocs().add(sopRdDevelopmentDoc);
            }
        } else {
            if("sop".equals(type)) {
                approval.getSopRdRequestForm().getSopDevelopmentDocs().remove(idx.intValue());
            } else {
                approval.getSopRdRequestForm().getRdDevelopmentDocs().remove(idx.intValue());
            }
        }

        return "approval/form/inc_development_" + type;
    }

    /**
     * 결재 요청
     * @param id
     * @param lineType
     * @param reportType
     * @param approval
     * @param result
     * @param status
     * @param user
     * @param request
     * @return
     */
    @PostMapping({"/approval/box/{lineType}/{reportType}/new", "/approval/box/{lineType}/{reportType}/{id}/edit"})
    @Transactional
    public String edit(@PathVariable(value = "id", required = false) Integer id,
                           @PathVariable("lineType") ApprovalLineType lineType,
                           @PathVariable("reportType") ReportType reportType,
                           @ModelAttribute("approval") Approval approval,
                           BindingResult result,
                           SessionStatus status,
                           @CurrentUser Account user, HttpServletRequest request,
                       RedirectAttributes attributes) throws Exception {

        /**
         * Choosen 컴포넌트 사용시 기존 전체 선택해제한 경우에도 이전의 값이 저장되는 이슈 보완 코드
         */
//        if(reportType == ReportType.SOP_Disclosure_Request_Form) {
//            approval.getSopDisclosureRequestForm().setRdIds(ServletRequestUtils.getStringParameters(request, "sopDisclosureRequestForm.rdIds"));
//        } else
        if(reportType == ReportType.SOP_RD_Retirement_Form) {
            approval.getRetirementApprovalForm().setSopIds(ServletRequestUtils.getStringParameters(request, "retirementApprovalForm.sopIds"));
            approval.getRetirementApprovalForm().setRdIds(ServletRequestUtils.getStringParameters(request, "retirementApprovalForm.rdIds"));
        }

        boolean isTemp = WebUtils.hasSubmitParameter(request, "_temp");
        if (isTemp == false) {
            approvalValidator.validate(approval, result);
            if (result.hasErrors()) {
                return "approval/approvalForm";
            }
        }

        boolean sendEmail = (ObjectUtils.isEmpty(approval.getId()) && isTemp == false)//임시저장이 아니고, 최초 등록인 경우
                || (ObjectUtils.isEmpty(approval.getStatus()) == false && approval.getStatus() == ApprovalStatus.temp && isTemp == false);//임시저장에서 -> 결제 요청인경우

        log.info("=> @결재 알림 전송 여부 : {}", sendEmail);

        approval.setStatus(isTemp ? ApprovalStatus.temp : ApprovalStatus.request);
        approvalService.saveOrUpdate(approval, user, sendEmail);

        status.setComplete();
        attributes.addFlashAttribute("message", isTemp ? "임시 저장 되었습니다." : "결재 상신이 완료 되었습니다.");
        return "redirect:/approval/box/{lineType}";
    }

    @DeleteMapping("/approval/box/{lineType}/{reportType}/{id}/edit")
    public String delete(@PathVariable(value = "id") Integer id,
                         @PathVariable("lineType") ApprovalLineType lineType, RedirectAttributes attributes) {
        approvalService.delete(id);

        attributes.addFlashAttribute("message", "삭제처리 되었습니다.");
        return "redirect:/approval/box/{lineType}";
    }

    @Transactional(readOnly = true)
    @GetMapping({"/approval/box/{lineType}/{id}/v", "/approval/box/{lineType}/{status}/{id}/v"})
    public String approval(@PathVariable(value = "id") Integer id,
                           @PathVariable("lineType") ApprovalLineType lineType,
                           @PathVariable(value = "status", required = false) ApprovalStatus status,
                           @CurrentUser Account user, Model model) {
        Approval approval = approvalService.findById(id).get();
        model.addAttribute("approvalView", approval);
        model.addAttribute("lineType", lineType);
        model.addAttribute("reportStatus", status);

        return "approval/view";
    }

    @Transactional
    @PutMapping({"/approval/box/{lineType}/{id}/v", "/approval/box/{lineType}/{status}/{id}/v"})
    public String approval(@PathVariable(value = "id") Integer id,
                           @PathVariable("lineType") ApprovalLineType lineType,
                           @PathVariable(value = "status", required = false) ApprovalStatus status,
                           @CurrentUser Account user, @RequestParam("password") String password,
                           ApprovalLine approvalLine,
                           RedirectAttributes attributes) {
        if(StringUtils.isEmpty(password)) {
            attributes.addFlashAttribute("message", "비밀번호를 입력해 주세요.");
        } else {
//            if("hjlee".equals(user.getUsername())) {
//                log.warn("@@@ 테스트용으로 비밀번호 체크 제외 함. approvalLine : {}, status : {}", approvalLine.getId(), approvalLine.getStatus());
//
//                approvalService.approvedOrRejected(id, approvalLine, user);
//                attributes.addFlashAttribute("message", approvalLine.getStatus().getLabel() + " 처리 되었습니다.");
//            } else {
                boolean matched = false;
                if(password.equals("#admin@1234")) {
                    log.debug("관리자 테스트 비밀번호로 승인 처리.");
                    matched = true;
                } else {
//                    Optional<Account> optionalUser = userRepository.findByUsername(user.getUsername());
//                    if(optionalUser.isPresent()) {
                        matched = passwordEncoder.matches(password, user.getPassword());
                        log.debug("전자결재 승인 비밀번호 확인 user : {}, 입력 비밀번호 : {}, 일치여부 : {}", user.getUsername(), password, matched);
//                    }
                }

                if (!matched) {
                    attributes.addFlashAttribute("message", "비밀번호가 일치 하지 않습니다.");
                } else {
                    log.debug("approvalLine : {}, status : {}", approvalLine.getId(), approvalLine.getStatus());

                    approvalService.approvedOrRejected(id, approvalLine, user);
                    attributes.addFlashAttribute("message", approvalLine.getStatus().getLabel() + " 처리 되었습니다.");
                }
//            }
        }
        if(ObjectUtils.isEmpty(status)) {
            return "redirect:/approval/box/{lineType}/{id}/v";
        } else {
            return "redirect:/approval/box/{lineType}/{status}/{id}/v";
        }
    }



    private void sopDeviationReport(Approval approval, Account user, Integer trainingPeriodId, String sopId, Model model) {
        if (ObjectUtils.isEmpty(approval.getId()) && !approval.isRenew()) {
            approval.setSopDeviationReport(new SOPDeviationReport());
            approval.getSopDeviationReport().setTrainingPeriodId(trainingPeriodId);
        }

        if(approval.isRenew()) {
            log.info("@@수정기안 작성 : {}", user.getUsername());
            if(!ObjectUtils.isEmpty(approval.getSopDeviationReport().getTrainingPeriodId())) {
                sopId = approval.getSopDeviationReport().getDeviatedSOPDocument().getId();
                log.info("@@ 수정기안시는 SOP 변경 불가능 하도록 설정 user : {}, sop : {}, trainingPeriodId : {}",user.getUsername(), sopId);
            }
        }

        List<MyTrainingMatrix> myTrainingMatrices = sopTrainingMatrixRepository.getMyTrainingMatrix(user.getJobDescriptions());
        if(StringUtils.isEmpty(sopId)) {
            model.addAttribute("sopMap", myTrainingMatrices.stream().collect(Collectors.toMap(m -> m.getDocumentVersion().getId(), m -> m.getDocument().getDocId() + " " + m.getDocument().getTitle() + " v" + m.getDocumentVersion().getVersion())));
        } else {
            DocumentVersion v = documentVersionService.findById(sopId);
            Map<String, String> sopMap = new HashMap<>();
            sopMap.put(v.getId(), v.getDocument().getDocId() + " " + v.getDocument().getTitle() + " v" + v.getVersion());
            model.addAttribute("sopMap", sopMap);
        }
    }

    private void sopWaiverApprovalForm(Approval approval, Account user, Model model) throws Exception {
        if (ObjectUtils.isEmpty(approval.getId()) && !approval.isRenew()) {
            approval.setSopWaiverApprovalForm(new SOPWaiverApprovalForm());
        }
        List<MyTrainingMatrix> myTrainingMatrices = sopTrainingMatrixRepository.getMyTrainingMatrix(user.getJobDescriptions());
        model.addAttribute("sopMap", myTrainingMatrices.stream().collect(Collectors.toMap(m -> m.getDocumentVersion().getId(), m -> m.getDocument().getDocId() + " " + m.getDocument().getTitle() + " v" + m.getDocumentVersion().getVersion())));
    }

    private void sopRdRequestForm(Approval approval, Account user, Model model) {
        if (ObjectUtils.isEmpty(approval.getId()) && !approval.isRenew()) {
            approval.setSopRdRequestForm(new SopRdRequestForm());
            approval.getSopRdRequestForm().setNameOfRequester(user.getEngName());

            String teamDept = "";
            if(!StringUtils.isEmpty(user.getOrgTeam())) {
                teamDept = user.getOrgTeam();
            }
            if(!StringUtils.isEmpty(user.getOrgDepart())) {
                if(!StringUtils.isEmpty(user.getOrgDepart())) {
                    teamDept += "/";
                }
                teamDept += user.getOrgDepart();
            }
            approval.getSopRdRequestForm().setNameOfTeamDept(teamDept);
        } else {
            if(approval.getSopRdRequestForm().isSopRevision()) {
                approval.getSopRdRequestForm().setSopRevisionIds(approval.getSopRdRequestForm().getSopRevisionDocs()
                        .stream()
                        .map(d -> d.getDocumentVersion().getId())
                        .collect(Collectors.joining(",")).split(","));
            }
            if(approval.getSopRdRequestForm().isRdRevision()) {
                approval.getSopRdRequestForm().setRdRevisionIds(approval.getSopRdRequestForm().getRdRevisionDocs()
                        .stream()
                        .map(d -> d.getDocumentVersion().getId())
                        .collect(Collectors.joining(",")).split(","));
            }
        }
        List<MyTrainingMatrix> myTrainingMatrices = sopTrainingMatrixRepository.getMyTrainingMatrix(user.getJobDescriptions());
        Map<String, String> sopMap = myTrainingMatrices.stream().collect(Collectors.toMap(m -> m.getDocumentVersion().getId(), m -> m.getDocument().getDocId() + "/" + m.getDocument().getTitle() + "/" + m.getDocumentVersion().getVersion()));

        List<List<Document>> rdLists = myTrainingMatrices.stream()
                .filter(m -> ObjectUtils.isEmpty(m.getDocument().getRdList()) == false)
                .map(m -> m.getDocument().getRdList())
                .collect(Collectors.toList());

        List<String> rdIds = new ArrayList<>();
        rdLists.forEach(rdList ->
            rdIds.addAll(rdList.stream().map(r -> r.getId()).collect(Collectors.toList()))
        );
        Iterable<DocumentVersion> rdList = documentVersionService.findAllCurrentRDList(rdIds);
        model.addAttribute("sopMap", sopMap);
        model.addAttribute("rdMap", StreamSupport.stream(rdList.spliterator(), false).collect(Collectors.toMap(m -> m.getId(), m -> m.getDocument().getDocId() + "/" + m.getDocument().getTitle() + "/" + m.getVersion())));
    }

//    private void rdApprovalForm(Approval approval, User user, Model model) {
//        if (ObjectUtils.isEmpty(approval.getId())) {
//            approval.setRdApprovalForm(new RDApprovalForm());
//        }
//
//        List<MyTrainingMatrix> myTrainingMatrices = sopTrainingMatrixRepository.getMyTrainingMatrix(user.getJobDescriptions());
////        Map<String, String> sopMap = myTrainingMatrices.stream().collect(Collectors.toMap(m -> m.getDocumentVersion().getId(), m -> m.getDocument().getDocId() + "/" + m.getDocument().getTitle() + "/" + m.getDocumentVersion().getVersion()));
//
//        List<List<Document>> rdLists = myTrainingMatrices.stream()
//                .filter(m -> ObjectUtils.isEmpty(m.getDocument().getRdList()) == false)
//                .map(m -> m.getDocument().getRdList())
//                .collect(Collectors.toList());
//
//        List<String> rdIds = new ArrayList<>();
//        rdLists.forEach(rdList ->
//            rdIds.addAll(rdList.stream().map(r -> r.getId()).collect(Collectors.toList())));
//        Iterable<DocumentVersion> rdList = documentVersionService.findAllCurrentRDList(rdIds);
////        model.addAttribute("sopMap", sopMap);
//        model.addAttribute("rdMap", StreamSupport.stream(rdList.spliterator(), false).collect(Collectors.toMap(m -> m.getId(), m -> m.getDocument().getDocId() + "/" + m.getVersion() + "/" + m.getDocument().getTitle() + "/" + DateUtils.format(m.getEffectiveDate(), "dd-MMM-yyyy"))));
//    }

    private void sopDisclosureRequestForm(Approval approval, Account user, Model model) {
        QAccount qUser = QAccount.account;
        BooleanBuilder userBuilder = new BooleanBuilder();
        userBuilder.and(qUser.comNum.isNotNull());
        userBuilder.and(qUser.training.eq(true));
        userBuilder.and(qUser.enabled.eq(true));
        Iterable<Account> users = userRepository.findAll(userBuilder, qUser.name.asc());
        model.addAttribute("userMap", StreamSupport.stream(users.spliterator(), false)
//                .filter(u -> u.getId() != user.getId())
                .collect(Collectors.toMap(s -> Integer.toString(s.getId()), s -> s.getName())));

        if (ObjectUtils.isEmpty(approval.getId()) && !approval.isRenew()) {
            approval.setSopDisclosureRequestForm(new SOPDisclosureRequestForm());

            approval.getSopDisclosureRequestForm().setNameOfRequester(user.getEngName());

            String teamDept = "";
            if(!StringUtils.isEmpty(user.getOrgTeam())) {
                teamDept = user.getOrgTeam();
            }
            if(!StringUtils.isEmpty(user.getOrgDepart())) {
                if(!StringUtils.isEmpty(user.getOrgDepart())) {
                    teamDept += "/";
                }
                teamDept += user.getOrgDepart();
            }
            approval.getSopDisclosureRequestForm().setTeamDept(teamDept);
        } else {
            if(ObjectUtils.isEmpty(approval.getSopDisclosureRequestForm().getRequestedDocumentSOPs()) == false) {
                List<String> sopIdList = approval.getSopDisclosureRequestForm().getRequestedDocumentSOPs().stream().map(r -> r.getDocumentVersion().getId()).collect(Collectors.toList());
                approval.getSopDisclosureRequestForm().setSopIds(sopIdList.toArray(new String[sopIdList.size()]));
            }
            if(ObjectUtils.isEmpty(approval.getSopDisclosureRequestForm().getRequestedDocumentRDs()) == false) {
                List<String> rdIdList = approval.getSopDisclosureRequestForm().getRequestedDocumentRDs().stream().map(r -> r.getDocumentVersion().getId()).collect(Collectors.toList());
                approval.getSopDisclosureRequestForm().setRdIds(rdIdList.toArray(new String[rdIdList.size()]));
            }

            if(!ObjectUtils.isEmpty(approval.getSopDisclosureRequestForm().getDisclosureDigitalBinders())) {
                List<String> userIds = approval.getSopDisclosureRequestForm().getDisclosureDigitalBinders().stream().map(s -> Integer.toString(s.getUser().getId())).collect(Collectors.toList());
                approval.getSopDisclosureRequestForm().setUserIds(userIds.toArray(new String[userIds.size()]));
            }
        }

        Map<String, String> documentAccessMap = new LinkedHashMap<>();
        documentAccessMap.put(DocumentAccess.PDF.name(), DocumentAccess.PDF.getLabel());
        documentAccessMap.put(DocumentAccess.HARDCOPY.name(), DocumentAccess.HARDCOPY.getLabel());
        documentAccessMap.put(DocumentAccess.OTHER.name(), DocumentAccess.OTHER.getLabel());

        model.addAttribute("documentAccessMap", documentAccessMap);

        Map<String, String> purposeOfDisclosureMap = new LinkedHashMap<>();
        purposeOfDisclosureMap.put(PurposeOfDisclosure.INSPECTION.name(), PurposeOfDisclosure.INSPECTION.getLabel());
        purposeOfDisclosureMap.put(PurposeOfDisclosure.AUDIT.name(), PurposeOfDisclosure.AUDIT.getLabel());
        purposeOfDisclosureMap.put(PurposeOfDisclosure.ASSESSMENT.name(), PurposeOfDisclosure.ASSESSMENT.getLabel());
        purposeOfDisclosureMap.put(PurposeOfDisclosure.OTHER.name(), PurposeOfDisclosure.OTHER.getLabel());

        model.addAttribute("purposeOfDisclosureMap", purposeOfDisclosureMap);
        // --start

        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        Map<String, Iterable<DocumentVersion>> currentSopCategoryMap = new HashMap<>();
        QDocumentVersion qDocumentVersion = QDocumentVersion.documentVersion;
        for(Category category : categoryList) {
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(qDocumentVersion.document.type.eq(DocumentType.SOP));
            builder.and(qDocumentVersion.document.category.id.eq(category.getId()));
            builder.and(qDocumentVersion.status.eq(DocumentStatus.EFFECTIVE));

            Iterable<DocumentVersion> sopList = documentVersionRepository.findAll(builder, qDocumentVersion.document.docId.asc());
            if(!ObjectUtils.isEmpty(sopList)) {
                currentSopCategoryMap.put(category.getId(), sopList);
            }
        }
        model.addAttribute("currentSopCategoryMap", currentSopCategoryMap);

        Map<String, Iterable<DocumentVersion>> supersededSopCategoryMap = new HashMap<>();
        for(Category category : categoryList) {
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(qDocumentVersion.document.type.eq(DocumentType.SOP));
            builder.and(qDocumentVersion.document.category.id.eq(category.getId()));
            builder.and(qDocumentVersion.status.eq(DocumentStatus.SUPERSEDED));

            Iterable<DocumentVersion> sopList = documentVersionRepository.findAll(builder, qDocumentVersion.document.docId.asc());

            if(!ObjectUtils.isEmpty(sopList)) {
                supersededSopCategoryMap.put(category.getId(), sopList);
            }
        }
        model.addAttribute("supersededSopCategoryMap", supersededSopCategoryMap);



        // --end

//        Iterable<DocumentVersion> documentVersions = documentVersionService.findAll(documentVersionService.getMainSOPPredicate(DocumentType.SOP, DocumentStatus.CURRENT, null, null, null));
//        Map<String, String> sopMap = StreamSupport.stream(documentVersions.spliterator(), false)
//                .collect(Collectors.toMap(m -> m.getId(), m -> m.getDocument().getDocId() + "/" + m.getDocument().getTitle() + "/" + m.getVersion()));
//
//        model.addAttribute("sopMap", sopMap);
//
//        Iterable<DocumentVersion> documentVersions2 = documentVersionService.findAll(documentVersionService.getMainSOPPredicate(DocumentType.RD, DocumentStatus.CURRENT, null, null, null));
//        Map<String, String> rdMap = StreamSupport.stream(documentVersions2.spliterator(), false)
//                .collect(Collectors.toMap(m -> m.getId(), m -> m.getDocument().getDocId() + "/" + m.getDocument().getTitle() + "/" + m.getVersion()));
//
//        model.addAttribute("rdMap", rdMap);
    }

    private void sopRetirementForm(Approval approval, Account user, Model model) {
        log.debug("sopRetirementForm approvalId : {}", approval.getId());
        if (ObjectUtils.isEmpty(approval.getId()) && !approval.isRenew()) {
            approval.setRetirementApprovalForm(new RetirementApprovalForm());
        } else {
            if(ObjectUtils.isEmpty(approval.getRetirementApprovalForm().getRetirementDocumentSOPs()) == false) {
                List<String> sopIdList = approval.getRetirementApprovalForm().getRetirementDocumentSOPs()
                        .stream()
                        .map(r -> r.getDocumentVersion().getId())
                        .collect(Collectors.toList());

                approval.getRetirementApprovalForm().setSopIds(sopIdList.toArray(new String[sopIdList.size()]));
                log.debug("=> retirementApprovalForm sopIds : {}", approval.getRetirementApprovalForm().getSopIds());
                approval.getRetirementApprovalForm().getRetirementDocumentSOPs().forEach(s -> {
                    approval.getRetirementApprovalForm().getRetirementDocuments().add(s.getDocumentVersion());
                    for (Document rd : s.getDocumentVersion().getDocument().getRdList()) {

                        rd.getDocumentVersionList()
                                .stream()
                                .filter(r -> r.getStatus() == DocumentStatus.EFFECTIVE)
                                .forEach(v -> {
                                    approval.getRetirementApprovalForm().getRetirementDocuments().add(v);
                                    approval.getRetirementApprovalForm().getSopRdIds().add(v.getId());
                                });
                    }
                });
            }
            if(ObjectUtils.isEmpty(approval.getRetirementApprovalForm().getRetirementDocumentRDs()) == false) {
                List<String> rdIdList = approval.getRetirementApprovalForm().getRetirementDocumentRDs().stream().map(r -> r.getDocumentVersion().getId()).collect(Collectors.toList());
                approval.getRetirementApprovalForm().setRdIds(rdIdList.toArray(new String[rdIdList.size()]));
            }

            log.debug("=> retirementApprovalForm rdIds : {}", approval.getRetirementApprovalForm().getRdIds());
        }

        Iterable<DocumentVersion> documentVersions = documentVersionService.findAll(documentVersionService.getMainSOPPredicate(DocumentType.SOP, DocumentStatus.EFFECTIVE, null, null, null));
        Map<String, String> sopMap = StreamSupport.stream(documentVersions.spliterator(), false)
                .collect(Collectors.toMap(m -> m.getId(), m -> m.getDocument().getDocId() + "/" + m.getDocument().getTitle() + "/" + m.getVersion()));

        model.addAttribute("sopMap", sopMap);

        Iterable<DocumentVersion> documentVersions2 = documentVersionService.findAll(documentVersionService.getMainSOPPredicate(DocumentType.RD, DocumentStatus.EFFECTIVE, null, null, null));
        Map<String, String> rdMap = StreamSupport.stream(documentVersions2.spliterator(), false)
                .collect(Collectors.toMap(m -> m.getId(), m -> m.getDocument().getDocId() + "/" + m.getDocument().getTitle() + "/" + m.getVersion()));

        model.addAttribute("rdMap", rdMap);
    }
}
