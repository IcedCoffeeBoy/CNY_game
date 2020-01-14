package com.dsta.CNYBackend.shared.model;

public class AdminErrorResponse extends ErrorResponse {
    private final String error ="ONLY ADMIN IS ALLOWED";

    public AdminErrorResponse() {
    }

    public String getError() {
        return error;
    }
}
