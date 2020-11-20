package com.cauh.iso.service;

import com.cauh.iso.domain.Mail;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public interface MailService {
    String processTemplate(String fileName, HashMap<String, Object> model, Locale locale);

    void sendMail(Mail mail);

    List<String> getReceiveEmails();
}
