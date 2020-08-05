package com.e.attandancesyatem;

public class TeachersAllottedResponse {

    String message;
    String classs;
    String subject;

    public TeachersAllottedResponse(String message, String classs, String subject) {
        this.message = message;
        this.classs = classs;
        this.subject = subject;
    }

    public TeachersAllottedResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
