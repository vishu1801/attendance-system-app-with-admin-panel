package com.e.attandancesyatem;


public class ClassSubject {

    private String classs;
    private String subject;

    public ClassSubject(String classs, String subject) {
        this.classs = classs;
        this.subject = subject;
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
