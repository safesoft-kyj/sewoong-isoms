package com.cauh.iso.service;

import com.cauh.common.entity.JobDescription;
import com.cauh.common.entity.Role;
import com.cauh.common.repository.JobDescriptionRepository;
import com.cauh.iso.domain.DocumentVersion;
import com.cauh.iso.domain.QSOPTrainingMatrix;
import com.cauh.iso.domain.TrainingMatrix;
import com.cauh.iso.domain.constant.DocumentType;
import com.cauh.iso.repository.SOPTrainingMatrixRepository;
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
        Iterable<TrainingMatrix> sopTrainingMatrices = sopTrainingMatrixRepository.findAll(builder);

        if(documentVersion.getDocument().getType() == DocumentType.SOP) {
            if (isAllTraining == false) {
                //선택된 JD(Id..)
                List<String> selectedIds = Arrays.asList(jdIds);
                //저장된 JD..
                List<String> savedIds = StreamSupport.stream(sopTrainingMatrices.spliterator(), false)
                        .filter(s -> s.isTrainingAll() == false && ObjectUtils.isEmpty(s.getRole()) == false)
                        .map(s -> Long.toString(s.getRole().getId()))
                        .collect(Collectors.toList());

                List<TrainingMatrix> removeList = StreamSupport.stream(sopTrainingMatrices.spliterator(), false)
                        .filter(s -> s.isTrainingAll() == true || ObjectUtils.isEmpty(s.getRole()) == false && selectedIds.contains(Long.toString(s.getRole().getId())) == false)
                        .collect(Collectors.toList());
                sopTrainingMatrixRepository.deleteAll(removeList);

                List<String> newIds = selectedIds.stream().filter(id -> savedIds.contains(id) == false).collect(Collectors.toList());
                log.info("=>신규 추가 JD ID(s) : {}", newIds);
                for (String id : newIds) {
                    TrainingMatrix trainingMatrix = new TrainingMatrix();
                    trainingMatrix.setDocumentVersion(documentVersion);
                    trainingMatrix.setRole(Role.builder().id(Long.parseLong(id)).build());

                    sopTrainingMatrixRepository.save(trainingMatrix);
                }
            } else {
                log.info(" => 기존에 지정된 JD 정보를 삭제한다. {}", documentVersion.getId());
                List<TrainingMatrix> trainingMatrixList = StreamSupport.stream(sopTrainingMatrices.spliterator(), false)
                        .collect(Collectors.toList());
                if(trainingMatrixList.size() == 1 && trainingMatrixList.stream().filter(s -> s.isTrainingAll() == true).count() == 1) {
                    log.debug("==> 이미 전체 트레이닝 SOP 설정 되어 있음. docVerId : {}", documentVersion.getId());
                } else {
                    sopTrainingMatrices.forEach(matrix -> {
                        log.debug("=> matrix 삭제 : {}", matrix.getId());
                        sopTrainingMatrixRepository.delete(matrix);
                    });
//                sopTrainingMatrixRepository.deleteAll(sopTrainingMatrices);
                    log.info(" <= 기존에 지정된 JD 삭제 완료 : {}", documentVersion.getId());

                    TrainingMatrix trainingMatrix = new TrainingMatrix();
                    trainingMatrix.setDocumentVersion(documentVersion);
                    trainingMatrix.setTrainingAll(true);

                    sopTrainingMatrixRepository.save(trainingMatrix);
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
