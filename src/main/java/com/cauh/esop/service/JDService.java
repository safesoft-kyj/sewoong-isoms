package com.cauh.esop.service;

import com.cauh.common.entity.JobDescription;
import com.cauh.common.repository.JobDescriptionRepository;
import com.cauh.esop.domain.DocumentVersion;
import com.cauh.esop.domain.QSOPTrainingMatrix;
import com.cauh.esop.domain.SOPTrainingMatrix;
import com.cauh.esop.domain.constant.DocumentType;
import com.cauh.esop.repository.SOPTrainingMatrixRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class JDService {
    private final JobDescriptionRepository jobDescriptionRepository;
    private final SOPTrainingMatrixRepository sopTrainingMatrixRepository;

    public void saveAll(DocumentVersion documentVersion, boolean isAllTraining, String[] jdIds) {
        QSOPTrainingMatrix qsopTrainingMatrix = QSOPTrainingMatrix.sOPTrainingMatrix;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qsopTrainingMatrix.documentVersion.id.eq(documentVersion.getId()));
        Iterable<SOPTrainingMatrix> sopTrainingMatrices = sopTrainingMatrixRepository.findAll(builder);

        if(documentVersion.getDocument().getType() == DocumentType.SOP) {
            if (isAllTraining == false) {
                //선택된 JD(Id..)
                List<String> selectedIds = Arrays.asList(jdIds);
                //저장된 JD..
                List<String> savedIds = StreamSupport.stream(sopTrainingMatrices.spliterator(), false)
                        .filter(s -> s.isTrainingAll() == false && ObjectUtils.isEmpty(s.getJobDescription()) == false)
                        .map(s -> Integer.toString(s.getJobDescription().getId()))
                        .collect(Collectors.toList());

                List<SOPTrainingMatrix> removeList = StreamSupport.stream(sopTrainingMatrices.spliterator(), false)
                        .filter(s -> s.isTrainingAll() == true || ObjectUtils.isEmpty(s.getJobDescription()) == false && selectedIds.contains(Integer.toString(s.getJobDescription().getId())) == false)
                        .collect(Collectors.toList());
                sopTrainingMatrixRepository.deleteAll(removeList);

                List<String> newIds = selectedIds.stream().filter(id -> savedIds.contains(id) == false).collect(Collectors.toList());
                log.info("=>신규 추가 JD ID(s) : {}", newIds);
                for (String id : newIds) {
                    SOPTrainingMatrix sopTrainingMatrix = new SOPTrainingMatrix();
                    sopTrainingMatrix.setDocumentVersion(documentVersion);
                    sopTrainingMatrix.setJobDescription(JobDescription.builder().id(Integer.parseInt(id)).build());

                    sopTrainingMatrixRepository.save(sopTrainingMatrix);
                }
            } else {
                log.info(" => 기존에 지정된 JD 정보를 삭제한다. {}", documentVersion.getId());
                List<SOPTrainingMatrix> sopTrainingMatrixList = StreamSupport.stream(sopTrainingMatrices.spliterator(), false)
                        .collect(Collectors.toList());
                if(sopTrainingMatrixList.size() == 1 && sopTrainingMatrixList.stream().filter(s -> s.isTrainingAll() == true).count() == 1) {
                    log.debug("==> 이미 전체 트레이닝 SOP 설정 되어 있음. docVerId : {}", documentVersion.getId());
                } else {
                    sopTrainingMatrices.forEach(matrix -> {
                        log.debug("=> matrix 삭제 : {}", matrix.getId());
                        sopTrainingMatrixRepository.delete(matrix);
                    });
//                sopTrainingMatrixRepository.deleteAll(sopTrainingMatrices);
                    log.info(" <= 기존에 지정된 JD 삭제 완료 : {}", documentVersion.getId());

                    SOPTrainingMatrix sopTrainingMatrix = new SOPTrainingMatrix();
                    sopTrainingMatrix.setDocumentVersion(documentVersion);
                    sopTrainingMatrix.setTrainingAll(true);

                    sopTrainingMatrixRepository.save(sopTrainingMatrix);
                }
            }
        }
    }

    public TreeMap<String, String> getJDMap() {
        Iterable<JobDescription> jobDescriptions = jobDescriptionRepository.findAll(Sort.by(Sort.Direction.ASC, "shortName"));
        Map<String, String> jobDescMap = StreamSupport.stream(jobDescriptions.spliterator(), false)
                .collect(Collectors.toMap(j -> Integer.toString(j.getId()), j -> j.getShortName()));

        TreeMap<String, String> sortedJDMap = new TreeMap<>();
        sortedJDMap.putAll(jobDescMap);

        return sortedJDMap;
    }
}
