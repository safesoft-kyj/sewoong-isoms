package com.cauh.iso.service;

import com.cauh.iso.domain.ISO;
import com.cauh.iso.repository.ISORepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ISOService {
    private ISORepository isoRepository;

    public Page<ISO> findAll(Pageable pageable) {
        return isoRepository.findAll(pageable);
    }


}
