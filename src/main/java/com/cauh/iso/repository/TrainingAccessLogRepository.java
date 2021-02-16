package com.cauh.iso.repository;

import com.cauh.iso.domain.TrainingAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingAccessLogRepository extends JpaRepository<TrainingAccessLog, String> {
}
