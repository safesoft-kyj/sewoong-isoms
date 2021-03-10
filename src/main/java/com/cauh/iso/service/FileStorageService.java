package com.cauh.iso.service;

import com.cauh.iso.exception.FileStorageException;
import com.cauh.iso.exception.MyFileNotFoundException;
import com.cauh.iso.property.FileStorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(Paths.get(fileStorageProperties.getTrainingLogUploadDir()).toAbsolutePath().normalize());
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, String prefix) {
        // Normalize file name
        String fileName = prefix + "_" + UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            log.info("PATH : {}", targetLocation);

//            System.out.println("targetLocation : " + targetLocation.toString());
            file.transferTo(targetLocation);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Boolean conversionHwp2Pdf(MultipartFile uploadedHwpFile, String id){


        return false;
    }

    public Integer conversionPdf2Img(MultipartFile uploadedPdfFile, String id) {
//        List<String> savedImgList = new ArrayList<>(); //저장된 이미지 경로를 저장하는 List 객체
        try (PDDocument pdfDoc = PDDocument.load(uploadedPdfFile.getInputStream())) {

            PDFRenderer pdfRenderer = new PDFRenderer(pdfDoc);

            //순회하며 이미지로 변환 처리
            for (int i = 0; i < pdfDoc.getPages().getCount(); i++) {
                String imgFileName = id + "-" +  + (i + 1) + ".jpg";
                log.debug("-> pdf2img[{}] = {}", i, imgFileName);
                Path targetLocation = this.fileStorageLocation.resolve(imgFileName);
                //DPI 설정
                BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 300, ImageType.RGB);

                // 이미지로 만든다.
                log.debug("SOP/ISO image 생성 : {}", targetLocation.toString());
                ImageIOUtil.writeImage(bim, targetLocation.toString() , 300);

                //저장 완료된 이미지를 list에 추가한다.
//                savedImgList.add(makeDownloadUrl4Uuid(imgFileName));
            }
            pdfDoc.close(); //모두 사용한 PDF 문서는 닫는다.

            return pdfDoc.getPages().getCount();
        }
        catch (FileNotFoundException e) { throw new RuntimeException("Pdf file not found. exception message = " + e.getMessage() ); }
        catch (IOException e) { throw new RuntimeException("Change fail pdf to image. IOException message = " + e.getMessage() ); }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}