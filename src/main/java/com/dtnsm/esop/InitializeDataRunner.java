package com.dtnsm.esop;

import com.dtnsm.common.entity.Account;
import com.dtnsm.common.entity.JobDescription;
import com.dtnsm.common.entity.JobDescriptionVersion;
import com.dtnsm.common.repository.JobDescriptionRepository;
import com.dtnsm.common.repository.JobDescriptionVersionRepository;
import com.dtnsm.common.repository.UserJobDescriptionRepository;
import com.dtnsm.common.repository.UserRepository;
import com.dtnsm.common.service.UserService;
import com.dtnsm.esop.domain.*;
import com.dtnsm.esop.domain.constant.DocumentStatus;
import com.dtnsm.esop.domain.constant.DocumentType;
import com.dtnsm.esop.domain.constant.TrainingType;
import com.dtnsm.esop.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.UUID;

@Component
@Slf4j
@Order(1)
@RequiredArgsConstructor
public class InitializeDataRunner implements ApplicationRunner {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String springJpaDDLAuto;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final UserRepository userRepository;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final UserJobDescriptionRepository userJobDescriptionRepository;
    private final CategoryRepository categoryRepository;
    private final DocumentRepository documentRepository;
//    private final PasswordEncoder passwordEncoder;
    private final DocumentVersionRepository documentVersionRepository;
    private final SOPTrainingMatrixRepository sopTrainingMatrixRepository;
    private final TrainingPeriodRepository trainingPeriodRepository;
    private final JobDescriptionVersionRepository jobDescriptionVersionRepository;

//    private final DeptUserMapper deptUserMapper;
    private final UserService userService;

    public void run(ApplicationArguments args) {
        log.info("springJpaDDLAuto => {}", springJpaDDLAuto);
        log.info("@Env : {}", activeProfile);

//        userService.sync();
//        if("local".equals(activeProfile)) {
//        } else if("prod".equals(activeProfile)) {
//            //TODO 향후 테스트 완료 후 해당 코드 삭제 필요!!!!
//            java.util.Date date = new java.util.Date();
//            JobDescription jd_qa = addJobDescription(1, "QAA", "Quality Assurance Associate");
//            JobDescriptionVersion jdv_qa = addJobDescriptionVersion(1, jd_qa, "1.0", date);
//
//            JobDescription jd_qmo = addJobDescription(2, "QMO", "Quality Management Officer");
//            JobDescriptionVersion jdv_qmo = addJobDescriptionVersion(2, jd_qmo, "1.0", date);
//
//            JobDescription jd_qmm = addJobDescription(3, "QAM", "Quality Assurance Manager");
//            JobDescriptionVersion jdv_qmm = addJobDescriptionVersion(3, jd_qmm, "1.0", date);
//
//            JobDescription jd_spm = addJobDescription(4, "SPM", "Software Project Manager");
//            JobDescriptionVersion jdv_spm = addJobDescriptionVersion(4, jd_spm, "1.0", date);
//
//            JobDescription jd_dm = addJobDescription(5, "DMA", "Data Management Associate");
//            JobDescription jd_cra = addJobDescription(6, "CRA", "Clinical Research Associate");
//            JobDescription jd_bda = addJobDescription(7, "BDA", "Business Development Associate");
//            JobDescription jd_cm = addJobDescription(8, "CM", "Central Monitor");
//            JobDescription jd_cda = addJobDescription(9, "CDA", "Clinical Data Associate");
//            JobDescription jd_mw = addJobDescription(10, "MW", "Medical Writer");
//
//            Optional<User> jhseo = userService.findByUsername("jhseo");
//            Optional<User> hjlim = userService.findByUsername("hjlim");
//            Optional<User> hjlee = userService.findByUsername("hjlee");
//            Optional<User> mhahn = userService.findByUsername("mhahn");
//            if(jhseo.isPresent()) {
//                addUserJobDescription(1, jhseo.get(), jdv_spm);
//                addUserJobDescription(6, jhseo.get(), jdv_qa);
//            }
//            if(hjlim.isPresent()) {
//                addUserJobDescription(2, hjlim.get(), jdv_qmm);
//                addUserJobDescription(3, hjlim.get(), jdv_qmo);
//            }
//            if(hjlee.isPresent()) {
//                addUserJobDescription(4, hjlee.get(), jdv_qa);
//            }
//            if(mhahn.isPresent()) {
//                addUserJobDescription(5, mhahn.get(), jdv_qa);
//            }
//        }
    }


    public DocumentVersion addDocumentVersion(Document document, String version, DocumentStatus status, Date effectiveDate) {
        DocumentVersion documentVersion = DocumentVersion.builder()
                .id(UUID.randomUUID().toString())
                .document(document)
                .version(version)
                .effectiveDate(effectiveDate)
                .status(status)
                .build();

        if(document.getType() == DocumentType.SOP) {
            documentVersion.setQuiz("{\"quizQuestions\":[{\"index\":1,\"text\":\"문제1\",\"answers\":[{\"index\":1,\"text\":\"보기1\",\"correct\":true},{\"index\":2,\"text\":\"보기2\",\"correct\":false},{\"index\":3,\"text\":\"보기3\",\"correct\":false},{\"index\":4,\"text\":\"보기4\",\"correct\":false},{\"index\":5,\"text\":\"보기5\",\"correct\":false}],\"correct\":[1]},{\"index\":2,\"text\":\"문제2\",\"answers\":[{\"index\":1,\"text\":\"보기1\",\"correct\":false},{\"index\":2,\"text\":\"보기2\",\"correct\":true},{\"index\":3,\"text\":\"보기3\",\"correct\":false},{\"index\":4,\"text\":\"보기4\",\"correct\":false},{\"index\":5,\"text\":\"보기5\",\"correct\":false}],\"correct\":[2]},{\"index\":3,\"text\":\"문제3\",\"answers\":[{\"index\":1,\"text\":\"보기1\",\"correct\":false},{\"index\":2,\"text\":\"보기2\",\"correct\":false},{\"index\":3,\"text\":\"보기3\",\"correct\":true},{\"index\":4,\"text\":\"보기4\",\"correct\":false},{\"index\":5,\"text\":\"보기5\",\"correct\":false}],\"correct\":[3]},{\"index\":4,\"text\":\"문제4\",\"answers\":[{\"index\":1,\"text\":\"보기1\",\"correct\":false},{\"index\":2,\"text\":\"보기2\",\"correct\":false},{\"index\":3,\"text\":\"보기3\",\"correct\":false},{\"index\":4,\"text\":\"보기4\",\"correct\":true},{\"index\":5,\"text\":\"보기5\",\"correct\":false}],\"correct\":[4]},{\"index\":5,\"text\":\"문제5\",\"answers\":[{\"index\":1,\"text\":\"보기1\",\"correct\":true},{\"index\":2,\"text\":\"보기2\",\"correct\":false},{\"index\":3,\"text\":\"보기3\",\"correct\":true},{\"index\":4,\"text\":\"보기4\",\"correct\":false},{\"index\":5,\"text\":\"보기5\",\"correct\":true}],\"correct\":[1,3,5]}]}");
        }

        return documentVersionRepository.save(documentVersion);
    }

    public Category addCategory(String shortName, String name) {
        Category category = Category.builder()
                .shortName(shortName)
                .name(name)
                .build();

        return categoryRepository.save(category);
    }

    public Document addSOP(String id, Category category, String documentNo, String title) {
        Document document = Document.builder()
                .id(id)
                .category(category)
                .documentNo(documentNo)
                .docId(DocumentType.SOP.name() +"-" + category.getShortName() + documentNo)
                .title(title)
                .type(DocumentType.SOP)
                .build();

        return documentRepository.save(document);
    }

    public Document addRD(Document sopDocument, String documentNo, String title) {
        Document rd = Document.builder()
                .sop(sopDocument)
                .documentNo(documentNo)
                .docId(sopDocument.getDocId() + "_RD" + documentNo)
                .title(title)
                .type(DocumentType.RD)
                .build();

        return documentRepository.save(rd);
    }

    public Account addUser(Integer id, String username, String korName, String engName, boolean admin, String empNo) {
        Account user = new Account();
                user.setId(id);
                user.setUsername(username);
                user.setName(korName);
                user.setEngName(engName);
                user.setAccountNonLocked(true);
//                .password(password)//"$2a$10$OC7F674J9iuP0q6Oh7X4WOFrazK1FdpuHJcEq4CQPXFKTuxTdLqYO"
                user.setEnabled(true);
                user.setAdmin(admin);
                user.setComNum(empNo);

        return userRepository.save(user);
    }

    public JobDescription addJobDescription(Integer id, String shortName, String title) {
        JobDescription jd = JobDescription.builder()
                .id(id)
                .shortName(shortName)
                .title(title)
                .build();

        return jobDescriptionRepository.save(jd);
    }

    public JobDescriptionVersion addJobDescriptionVersion(Integer id, JobDescription jobDescription, String version, java.util.Date releaseDate) {
        JobDescriptionVersion jobDescriptionVersion = new JobDescriptionVersion();
        jobDescriptionVersion.setId(id);
        jobDescriptionVersion.setJobDescription(jobDescription);
        jobDescriptionVersion.setVersion_no(version);
        jobDescriptionVersion.setRelease_date(releaseDate);

        return jobDescriptionVersionRepository.save(jobDescriptionVersion);
    }

//    public UserJobDescription addUserJobDescription(Integer id, Account user, JobDescriptionVersion jobDescriptionVersion) {
//        QUserJobDescription qUserJobDescription = QUserJobDescription.userJobDescription;
//        BooleanBuilder builder = new BooleanBuilder();
//        builder.and(qUserJobDescription.username.eq(user.getUsername()).and(qUserJobDescription.jobDescriptionVersion.id.eq(jobDescriptionVersion.getId())));
//        boolean isPresent = userJobDescriptionRepository.findOne(builder).isPresent();
//        log.debug("UserJobDescription [{}], JD-id : {}, isPresent : {}", user.getUsername(), jobDescriptionVersion.getId(), isPresent);
//        if(isPresent == false) {
//            UserJobDescription userJd = new UserJobDescription();
//            userJd.setId(id);
//            userJd.setUsername(user.getUsername());
//            userJd.setJobDescriptionVersion(jobDescriptionVersion);
//            userJd.setAssignDate(user.getIndate());
//
//            log.debug(" ==> insert : {}", userJd);
//            return userJobDescriptionRepository.save(userJd);
//        } else {
//            return null;
//        }
//    }


    public TrainingPeriod addTrainingPeriod(DocumentVersion documentVersion, java.util.Date startDate, java.util.Date endDate, TrainingType trainingType) {
        TrainingPeriod trainingPeriod = TrainingPeriod.builder()
                .documentVersion(documentVersion)
                .startDate(startDate)
                .endDate(endDate)
                .trainingType(trainingType)
                .build();

        return trainingPeriodRepository.save(trainingPeriod);
    }

    public void addTrainingMatrix(DocumentVersion documentVersion) {
        SOPTrainingMatrix sopTrainingMatrix = SOPTrainingMatrix.builder()
                .documentVersion(documentVersion)
                .trainingAll(true)
                .build();

        sopTrainingMatrixRepository.save(sopTrainingMatrix);
    }



}
