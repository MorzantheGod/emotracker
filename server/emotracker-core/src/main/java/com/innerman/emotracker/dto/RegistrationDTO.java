package com.innerman.emotracker.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * User: petrpopov
 * Date: 27.02.14
 * Time: 22:42
 */

public class RegistrationDTO {

    @NotBlank
    private String fullName;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

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
}
