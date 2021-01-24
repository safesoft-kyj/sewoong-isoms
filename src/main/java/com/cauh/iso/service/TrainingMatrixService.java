package com.cauh.iso.service;

import com.cauh.iso.domain.TrainingMatrixFile;
import com.cauh.iso.repository.TrainingMatrixFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingMatrixService {
    private final TrainingMatrixFileRepository trainingMatrixFileRepository;
    private final FileStorageService fileStorageService;
    public Page<TrainingMatrixFile> findAll(Pageable pageable) {
        return trainingMatrixFileRepository.findAll(pageable);
    }

    /**
     * 가장 최신 파일을 가져온다.
     * @return
     */
    public Optional<TrainingMatrixFile> findFirstByOrderByIdDesc() {
        return trainingMatrixFileRepository.findFirstByOrderByIdDesc();
    }

    public void save(TrainingMatrixFile trainingMatrixFile) {
        String fileName = fileStorageService.storeFile(trainingMatrixFile.getUploadFile(), "trainingMatrix");
        trainingMatrixFile.setFileName(fileName);
        trainingMatrixFile.setFileType(trainingMatrixFile.getUploadFile().getContentType());
        trainingMatrixFile.setFileSize(trainingMatrixFile.getUploadFile().getSize());
        trainingMatrixFile.setOriginalFileName(trainingMatrixFile.getUploadFile().getOriginalFilename());

        trainingMatrixFileRepository.save(trainingMatrixFile);
    }

    public Optional<TrainingMatrixFile> findById(Integer id) {
        return trainingMatrixFileRepository.findById(id);
    }
}
