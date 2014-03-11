package com.innerman.emotracker.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by petrpopov on 11.03.14.
 */
public class UserDataDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String tokenValue;

    private List<SensorDTO> data;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public List<SensorDTO> getData() {
        return data;
    }

    public void setData(List<SensorDTO> data) {
        this.data = data;
    }
}
