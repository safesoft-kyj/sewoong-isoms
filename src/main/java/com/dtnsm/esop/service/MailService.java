package com.dtnsm.esop.service;

import com.dtnsm.esop.domain.Mail;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public interface MailService {
    String processTemplate(String fileName, HashMap<String, Object> model, Locale locale);

    void sendMail(Mail mail);

    List<String> getReceiveEmails();
}
