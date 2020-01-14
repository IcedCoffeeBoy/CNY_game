package com.dsta.CNYBackend.shared.model;

public class ErrorResponse {
    private String error = "ERROR";


    public ErrorResponse(String error) {
        this.error = error;
    }

    public ErrorResponse() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
