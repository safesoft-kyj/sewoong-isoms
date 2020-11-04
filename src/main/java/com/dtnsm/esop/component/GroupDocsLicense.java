package com.dtnsm.esop.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;

@Component
@Slf4j
public class GroupDocsLicense {
    @Value("${groupdocs.license}")
    private String licensePath;

    @PostConstruct
    public void apply() {
        try {
//            FileInputStream licenseFileInputStream = new FileInputStream(new File(license));
            log.info("@License Loaded. {}", licensePath);
            com.groupdocs.conversion.License conversionLicense = new com.groupdocs.conversion.License();
            conversionLicense.setLicense(new FileInputStream(new File(licensePath)));
            log.info("@Apply Conversion License.");
            com.groupdocs.assembly.License assemblyLicense = new com.groupdocs.assembly.License();
            assemblyLicense.setLicense(new FileInputStream(new File(licensePath)));
        } catch (Exception error) {
            log.error("Error : {}", error.getMessage());
        }
    }
}
