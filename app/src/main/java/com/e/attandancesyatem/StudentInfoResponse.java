package com.e.attandancesyatem;

public class StudentInfoResponse {
    String message;
    String firstname;
    String lastname;

    public StudentInfoResponse(String message,String firstname, String lastname) {
        this.message = message;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public StudentInfoResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
