package com.cauh.iso.repository;

import com.cauh.iso.domain.TrainingMatrixFile;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TrainingMatrixFileRepository extends PagingAndSortingRepository<TrainingMatrixFile, Integer> {
    Optional<TrainingMatrixFile> findFirstByOrderByIdDesc();
}
