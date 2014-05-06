package com.innerman.emotracker.core.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * User: petrpopov
 * Date: 27.02.14
 * Time: 22:42
 */

public class RegistrationDTO {

    @NotBlank
    @NotNull
    @Length(min = 5, max = 250)
    private String fullName;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 250)
    private String userName;

    @NotBlank
    @NotNull
    @Length(min = 6, max = 250)
    private String password;

    @NotBlank
    @NotNull
    @Email
    private String email;

    @NotBlank
    @NotNull
    private String tokenId;

    @NotBlank
    @NotNull
    private String key;

    @NotBlank
    @NotNull
    private String token;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
