package com.dtnsm.common.config;

import freemarker.ext.jsp.TaglibFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ClassPathTldsLoader {
    private static final String SECURITY_TLD = "/META-INF/security.tld";
    //    private static final String TILES_TLD = "/META-INF/tld/tiles-jsp.tld";
    final private List<String> classPathTlds;

    public ClassPathTldsLoader(String... classPathTlds) {
        super();
        if (classPathTlds.length == 0) {
            this.classPathTlds = Arrays.asList(SECURITY_TLD);
        } else {
            this.classPathTlds = Arrays.asList(classPathTlds);
        }
    }

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    @PostConstruct
    public void loadClassPathTlds() {
        TaglibFactory taglibFactory = freeMarkerConfigurer.getTaglibFactory();
        log.debug("taglibFactory : {}", taglibFactory);
        taglibFactory.setClasspathTlds(classPathTlds);
        log.debug("taglibFactory.getObjectWrapper() : {}", taglibFactory.getObjectWrapper());
    }
}

