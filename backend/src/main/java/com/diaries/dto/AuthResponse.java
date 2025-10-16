package com.diaries.dto;

/**
 * DTO for authentication responses.
 * Contains JWT token and user information.
 */
public class AuthResponse {

    private String token;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private UserDto user;

    public AuthResponse() {
    }

    public AuthResponse(String token, Long expiresIn, UserDto user) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
