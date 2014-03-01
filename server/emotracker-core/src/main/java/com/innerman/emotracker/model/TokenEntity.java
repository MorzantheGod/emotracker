package com.innerman.emotracker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by petrpopov on 01.03.14.
 */

@Document(collection = "tokens")
public class TokenEntity {

    @Id
    private String id;

    private String key;
    private String token;

    private Boolean eternal;
    private Date validTo;
    private Boolean valid;

    public TokenEntity() {
    }

    public TokenEntity(String key, String token) {
        this.key = key;
        this.token = token;
        this.eternal = Boolean.TRUE;
        this.valid = Boolean.TRUE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getEternal() {
        return eternal;
    }

    public void setEternal(Boolean eternal) {
        this.eternal = eternal;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
