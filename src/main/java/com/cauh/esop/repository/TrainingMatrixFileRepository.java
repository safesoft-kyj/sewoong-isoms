package com.cauh.esop.repository;

import com.cauh.esop.domain.TrainingMatrixFile;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TrainingMatrixFileRepository extends PagingAndSortingRepository<TrainingMatrixFile, Integer> {
    Optional<TrainingMatrixFile> findFirstByOrderByIdDesc();
}
