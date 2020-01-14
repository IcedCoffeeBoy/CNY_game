package com.dsta.CNYBackend.shared.model;

public class LoginErrorResponse extends ErrorResponse{
    private final String error = "You need to login";

    public LoginErrorResponse() {
    }

    public String getError() {
        return error;
    }
}
