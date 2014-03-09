package com.innerman.emotracker.model;

import java.util.List;

/**
 * Created by petrpopov on 09.03.14.
 */
public class UserDTO {

    private String id;

    private String fullName;
    private String userName;

    private String email;

    private String passwordHash;
    private String salt;

    private List<UserRoleDTO> roles;
    private List<TokenDTO> tokens;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<UserRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleDTO> roles) {
        this.roles = roles;
    }

    public List<TokenDTO> getTokens() {
        return tokens;
    }

    public void setTokens(List<TokenDTO> tokens) {
        this.tokens = tokens;
    }
}
