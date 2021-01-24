package com.cauh.iso.repository;

import com.cauh.iso.domain.ISOTrainingMatrixFile;
import com.cauh.iso.domain.TrainingMatrixFile;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ISOTrainingMatrixFileRepository extends PagingAndSortingRepository<ISOTrainingMatrixFile, Integer> {
    Optional<ISOTrainingMatrixFile> findFirstByOrderByIdDesc();

}
