package com.e.attandancesyatem;

public class UserValidationResponse {

    String message;

    public UserValidationResponse(String message) {
        this.message = message;
    }

    public UserValidationResponse() {
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
