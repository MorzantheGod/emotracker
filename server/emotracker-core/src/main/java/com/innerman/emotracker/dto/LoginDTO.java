package com.innerman.emotracker.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by petrpopov on 01.03.14.
 */
public class LoginDTO {

    @NotBlank
    @NotNull
    private String userName;

    @NotBlank
    @NotNull
    private String password;

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
}
