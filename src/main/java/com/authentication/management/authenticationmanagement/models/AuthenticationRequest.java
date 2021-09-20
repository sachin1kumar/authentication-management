package com.authentication.management.authenticationmanagement.models;

public class AuthenticationRequest {

    private String emailId;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailId() {
        return emailId;
    }
}
