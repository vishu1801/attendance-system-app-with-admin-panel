package com.e.attandancesyatem;

public class StudentInfo {
    String firstname;
    String lastname;
    boolean isclicked;

    public boolean isIsclicked() {
        return isclicked;
    }

    public void setIsclicked(boolean isclicked) {
        this.isclicked = isclicked;
    }

    public StudentInfo(String firstname, String lastname, boolean isclicked) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.isclicked=isclicked;
    }

    public StudentInfo(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.isclicked=false;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
