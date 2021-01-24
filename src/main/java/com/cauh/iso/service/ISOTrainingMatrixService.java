package com.cauh.iso.service;

import com.cauh.common.entity.Account;
import com.cauh.iso.domain.ISO;
import com.cauh.iso.domain.ISOTrainingMatrix;
import com.cauh.iso.domain.ISOTrainingMatrixFile;
import com.cauh.iso.repository.ISOTrainingMatrixFileRepository;
import com.cauh.iso.repository.ISOTrainingMatrixRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ISOTrainingMatrixService {

    private final ISOTrainingMatrixRepository isoTrainingMatrixRepository;
    private final ISOTrainingMatrixFileRepository isoTrainingMatrixFileRepository;

    public void saveAll(ISO savedISO, ISO iso) {

        if(ObjectUtils.isEmpty(iso.getIsoTrainingMatrix())) { //신규 생성
            if (iso.isTrainingAll()) { //전원 참석
                ISOTrainingMatrix isoTrainingMatrix = ISOTrainingMatrix.builder()
                        .trainingAll(true)
                        .iso(savedISO).build();
                isoTrainingMatrixRepository.save(isoTrainingMatrix);
            } else { //전원 참석이 아닌 경우,
                List<String> userIdList = Arrays.asList(iso.getUserIds());
                for (String userId : userIdList) {
                    ISOTrainingMatrix isoTrainingMatrix = ISOTrainingMatrix.builder()
                            .trainingAll(false)
                            .iso(savedISO)
                            .user(Account.builder().id(Integer.parseInt(userId)).build())
                            .build();
                    isoTrainingMatrixRepository.save(isoTrainingMatrix);
                }
            }
        } else { //수정


            if (iso.isTrainingAll()) { //전원 참석
                // CASE 1. 전원 참석 -> 전원 참석
                // 동작 필요 없음.

                // CASE 2. 부분 참석 -> 전원 참석
                // 기존에 유저별 참석이었을 경우, 전부 삭제 후 trainingAll로 생성
                if(iso.getIsoTrainingMatrix().stream().filter(tm -> tm.isTrainingAll()).count() <= 0) {
                    isoTrainingMatrixRepository.deleteAllByIso(iso.getId());

                    ISOTrainingMatrix isoTrainingMatrix = ISOTrainingMatrix.builder()
                            .trainingAll(true)
                            .iso(savedISO).build();
                    isoTrainingMatrixRepository.save(isoTrainingMatrix);
                }
            } else { //전원 참석이 아닌 경우,
                List<ISOTrainingMatrix> isoTrainingMatrixList = iso.getIsoTrainingMatrix();

                //기존에 참석자가 전원인 경우,
                if(isoTrainingMatrixList.stream().filter(tm -> tm.isTrainingAll()).count() > 0) {
                    // CASE 3. 전원 참석 -> 부분 참석
                    isoTrainingMatrixRepository.delete(isoTrainingMatrixList.stream().filter(tm -> tm.isTrainingAll()).findFirst().get());
                    List<String> ids = Arrays.asList(iso.getUserIds());

                    //신규 참석자 생성
                    for(String id : ids) {
                        ISOTrainingMatrix isoTrainingMatrix = ISOTrainingMatrix.builder()
                                .iso(savedISO)
                                .trainingAll(false)
                                .user(Account.builder().id(Integer.parseInt(id)).build())
                                .build();
                        isoTrainingMatrixRepository.save(isoTrainingMatrix);
                    }


                } else { //기존 참석자가 정해진 경우
                    // CASE 4. 부분 참석 -> 부분 참석
                    List<String> originIds = isoTrainingMatrixList.stream().filter(tm -> !tm.isTrainingAll()).map(tm -> Integer.toString(tm.getUser().getId())).collect(Collectors.toList());
                    List<String> ids = Arrays.asList(iso.getUserIds());

                    //기존 id명단에 없는 id 목록 -> 신규 대상
                    List<String> newIds = ids.stream().filter(id -> !originIds.contains(id)).collect(Collectors.toList());

                    //신규 id명단에 없는 ISOTrainingMatrix -> 삭제 대상
                    List<ISOTrainingMatrix> removeList = isoTrainingMatrixList.stream().filter(tm -> !ids.contains(Integer.toString(tm.getUser().getId()))).collect(Collectors.toList());

                    //신규 참석자 생성
                    for(String id : newIds) {
                        ISOTrainingMatrix isoTrainingMatrix = ISOTrainingMatrix.builder()
                                .iso(savedISO)
                                .trainingAll(false)
                                .user(Account.builder().id(Integer.parseInt(id)).build())
                                .build();
                        isoTrainingMatrixRepository.save(isoTrainingMatrix);
                    }

                    //삭제 대상 삭제
                    for(ISOTrainingMatrix matrix : removeList) {
                        isoTrainingMatrixRepository.delete(matrix);
                    }
                }
            }
        }
    }

    public Optional<ISOTrainingMatrixFile> findFirstByOrderByIdDesc() {
        return isoTrainingMatrixFileRepository.findFirstByOrderByIdDesc();
    }
}
