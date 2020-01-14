package com.dsta.CNYBackend.shared.model;

public class ErrorResponse {
    private String error = "ERROR";
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
