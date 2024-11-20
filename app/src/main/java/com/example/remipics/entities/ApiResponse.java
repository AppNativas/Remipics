package com.example.remipics.entities;

public class ApiResponse {

    private boolean success;
    private String message;
    private String token;
    private Object data;
    private Object error;

    //Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getData() { return data; }

    public void setData(Object data) { this.data = data; }

    public Object getError() { return error; }

    public void setError(Object error) { this.error = error; }

}
