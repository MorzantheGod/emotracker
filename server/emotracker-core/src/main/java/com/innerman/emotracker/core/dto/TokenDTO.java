package com.innerman.emotracker.core.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by petrpopov on 01.03.14.
 */
public class TokenDTO implements Serializable {

    @NotNull
    @NotBlank
    private String key;

    @NotNull
    @NotBlank
    private String token;

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
