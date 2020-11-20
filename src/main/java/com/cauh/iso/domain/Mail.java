package com.cauh.iso.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Locale;

@Data
@NoArgsConstructor
@ToString(of = {"to", "subject", "templateName"})
public class Mail {
    private String[] to;
    private String subject;
    private String templateName;
    private HashMap<String, Object> model;
    private Locale locale;
    private String[] cc;
    private String[] bcc;


    @Builder
    public Mail(String[] to, String[] cc, String[] bcc, String subject, String templateName, HashMap<String, Object> model, Locale locale) {
        this.to = to;
        this.subject = subject;
        this.templateName = templateName;
        this.model = model;
        this.locale = locale;
        this.cc = cc;
        this.bcc = bcc;
    }
}
