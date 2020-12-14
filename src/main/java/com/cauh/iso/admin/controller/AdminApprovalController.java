package com.cauh.iso.admin.controller;

import com.cauh.common.repository.UserRepository;
import com.cauh.iso.domain.Approval;
import com.cauh.iso.domain.constant.ApprovalStatus;
import com.cauh.iso.domain.constant.ReportType;
import com.cauh.iso.service.ApprovalService;
import com.cauh.iso.admin.service.UserJobDescriptionService;
import com.cauh.iso.xdocreport.SOPDisclosureRequestFormService;
import com.cauh.iso.xdocreport.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping({"/admin", "/ajax/admin"})
@RequiredArgsConstructor
public class AdminApprovalController {
    private final ApprovalService approvalService;
    private final SOPDisclosureRequestFormService sopDisclosureRequestFormService;
    private final SOPDeviationReportService sopDeviationReportService;
    private final UserRepository userRepository;
    private final SopRdRequestFormService sopRdRequestFormService;
    private final SOPWaiverRequestApprovalFormService sopWaiverRequestApprovalFormService;
    private final UserJobDescriptionService userJobDescriptionService;
    private final SopRdRetirementApprovalFormService sopRdRetirementApprovalFormService;

    @GetMapping({"/approval", "/approval/{status}"})
    public String totalList(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 15) Pageable pageable,
                            @PathVariable(value = "status", required = false) ApprovalStatus status,
                            @RequestParam(value = "type", required = false) String type,
                            Model model) {
        ReportType reportType = null;
        if(!StringUtils.isEmpty(type)) {
            reportType = ReportType.valueOf(type);
        }
        model.addAttribute("approvalList", approvalService.findAllAdmin(status, reportType, pageable));
        model.addAttribute("status", status);

//        model.addAttribute("reportType", Arrays.stream(ReportType.values()).collect(Collectors.toMap(a -> a.getViewName(), a -> a.getLabel())));
        model.addAttribute("reportTypeList", Arrays.asList(ReportType.values()));
        model.addAttribute("reportType", type);
        return "admin/approval/totalList";
    }

    @Transactional(readOnly = true)
    @GetMapping({"/approval/{id}/v", "/approval/{status}/{id}/v"})
    public String approval(@PathVariable(value = "id") Integer id,
                           @PathVariable(value = "status", required = false) ApprovalStatus status,
                           Model model) {
        Approval approval = approvalService.findById(id).get();
        model.addAttribute("approvalView", approval);
        model.addAttribute("status", status);

        return "admin/approval/view";
    }

    @DeleteMapping({"/approval/{id}/v", "/approval/{status}/{id}/v"})
    public String delete(@PathVariable(value = "id") Integer id,
                         @PathVariable(value = "status", required = false) ApprovalStatus status, RedirectAttributes attributes) {
        approvalService.delete(id);

        attributes.addFlashAttribute("message", "삭제 되었습니다.");
        if(ObjectUtils.isEmpty(status)) {
            return "redirect:/admin/approval";
        } else {
            return "redirect:/admin/approval/{status}";
        }
    }

    @GetMapping("/approval/{id}/print")
    @Transactional(readOnly = true)
    public void generateReport(@PathVariable(value = "id") Integer id,
                               HttpServletResponse response) throws Exception {

        Optional<Approval> optionalApproval = approvalService.findById(id);
        if(optionalApproval.isPresent()) {
            Approval approval = optionalApproval.get();
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+approval.getType().getLabel()+".pdf");
            approval.getApprovalLines().forEach(a ->
                a.setUser(userRepository.findByUsername(a.getUsername()).get()));

            if(approval.getType() != ReportType.SOP_RD_Retirement_Form) {
                response.setContentType("application/pdf");
            }

            if(approval.getType() == ReportType.SOP_Disclosure_Request_Form) {
                sopDisclosureRequestFormService.generateReport(approval.getSopDisclosureRequestForm(), approval.getApprovalLines(), response.getOutputStream());
            } else if(approval.getType() == ReportType.SOP_Deviation_Report) {
                sopDeviationReportService.generateReport(approval.getSopDeviationReport(), approval.getApprovalLines(), response.getOutputStream());
            } else if(approval.getType() == ReportType.SOP_RD_Request_Form) {
                sopRdRequestFormService.generateReport(approval.getSopRdRequestForm(), approval.getApprovalLines(), response.getOutputStream());
            } else if(approval.getType() == ReportType.SOP_Waiver_Approval_Form) {
                sopWaiverRequestApprovalFormService.generateReport(approval.getSopWaiverApprovalForm(), approval.getApprovalLines(), response.getOutputStream());
            } else if(approval.getType() == ReportType.SOP_RD_Retirement_Form) {
//                List<MyTraining> trainingList = sopTrainingMatrixRepository.getDownloadTrainingList(user.getDeptCode(), teamCode, userId, docId, user);
                sopRdRetirementApprovalFormService.generateReport(approval.getRetirementApprovalForm(), approval.getApprovalLines(), response.getOutputStream());
//                RetirementApprovalForm retirementApprovalForm = approval.getRetirementApprovalForm();
//                List<RetirementDocument> retirementDocuments = new ArrayList<>();
//                if(!ObjectUtils.isEmpty(retirementApprovalForm.getRetirementDocumentSOPs())) {
//                    retirementDocuments.addAll(retirementApprovalForm.getRetirementDocumentSOPs());
//                }
//                if(!ObjectUtils.isEmpty(retirementApprovalForm.getRetirementDocumentRDs())) {
//                    retirementDocuments.addAll(retirementApprovalForm.getRetirementDocumentRDs());
//                }
//                InputStream is = IndexReportService.class.getResourceAsStream("Retirement_Approval_Form.xlsx");
//                ApprovalLine preparedBy = approval.getApprovalLines().get(0);
//                int size = approval.getApprovalLines().size();
//                Context context = new Context();
//                context.putVar("retirementDocs", retirementDocuments);
//                context.putVar("reason", retirementApprovalForm.getReason());
//
//                context.putVar("preSign", Base64Utils.decodeBase64ToBytes(preparedBy.getBase64signature()));
//                context.putVar("preName", preparedBy.getUser().getEngName());
//                context.putVar("preTitle", userJobDescriptionService.getUserShortJobD(preparedBy.getUser().getUsername()));
//                context.putVar("preDate", preparedBy.getStrDate());
//
//                if(size == 3) {
//                    ApprovalLine revBy = approval.getApprovalLines().get(1);
//                    context.putVar("revSign", Base64Utils.decodeBase64ToBytes(revBy.getBase64signature()));
//                    context.putVar("revName", revBy.getUser().getEngName());
//                    context.putVar("revTitle", userJobDescriptionService.getUserShortJobD(revBy.getUser().getUsername()));
//                    context.putVar("revDate", revBy.getStrDate());
//                }
//                ApprovalLine appBy = approval.getApprovalLines().get(size - 1);
//                context.putVar("appSign", Base64Utils.decodeBase64ToBytes(appBy.getBase64signature()));
//                context.putVar("appName", appBy.getUser().getEngName());
//                context.putVar("appTitle", userJobDescriptionService.getUserShortJobD(appBy.getUser().getUsername()));
//                context.putVar("appDate", appBy.getStrDate());
//
//                response.setHeader("Content-Disposition", "attachment; filename=\"Retirement Approval Form.xlsx\"");
//                JxlsHelper.getInstance().processTemplate(is, response.getOutputStream(), context);
            }
        }
    }
}
