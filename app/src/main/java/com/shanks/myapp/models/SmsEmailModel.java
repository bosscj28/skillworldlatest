package com.shanks.myapp.models;

/**
 * Created by ankitpurohit on 13-04-2017.
 */

public class SmsEmailModel {
    private String email_subject = "";
    private String smsContent = "";
    private String email_content = "";

    public String getEmail_subject() {
        return email_subject;
    }

    public void setEmail_subject(String email_subject) {
        this.email_subject = email_subject;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getEmail_content() {
        return email_content;
    }

    public void setEmail_content(String email_content) {
        this.email_content = email_content;
    }
}
