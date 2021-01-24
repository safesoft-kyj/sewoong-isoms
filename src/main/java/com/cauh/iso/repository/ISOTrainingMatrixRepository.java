package com.cauh.iso.repository;

import com.cauh.iso.domain.ISO;
import com.cauh.iso.domain.ISOTrainingMatrix;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ISOTrainingMatrixRepository extends PagingAndSortingRepository<ISOTrainingMatrix, Integer>, QuerydslPredicateExecutor<ISOTrainingMatrix> {

    @Transactional
    @Modifying
    @Query("delete from ISOTrainingMatrix tm WHERE tm.iso.id = :isoId")
    Integer deleteAllByIso(@Param("isoId") String isoId);

}
