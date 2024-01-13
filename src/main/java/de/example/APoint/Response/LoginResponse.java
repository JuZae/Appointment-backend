package de.example.APoint.Response;

import de.example.APoint.Entity.User;

import java.util.UUID;

public class LoginResponse {
    private String message;
    private Boolean status;
    private String token; //JWT Token
    private UUID userId;

    public LoginResponse(String message, Boolean status, String token, UUID userId) {
        this.message    = message;
        this.status     = status;
        this.token      = token;
        this.userId     = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}