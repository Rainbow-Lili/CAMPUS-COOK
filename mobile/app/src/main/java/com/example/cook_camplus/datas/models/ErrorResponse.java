package com.example.cook_camplus.datas.models;

public class ErrorResponse {
    private int status;
    private String message;
    private String error;
    private String timestamp;

    public ErrorResponse() {}

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters & Setters
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    // Helper
    public String getDisplayMessage() {
        if (message != null && !message.isEmpty()) {
            return message;
        }
        if (error != null && !error.isEmpty()) {
            return error;
        }
        return "Une erreur est survenue";
    }
}
