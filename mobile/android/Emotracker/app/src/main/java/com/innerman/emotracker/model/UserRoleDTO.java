package com.innerman.emotracker.model;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by petrpopov on 09.03.14.
 */
public class UserRoleDTO {

    private String name;
    @JsonIgnore
    private String basicDBObject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBasicDBObject() {
        return basicDBObject;
    }

    public void setBasicDBObject(String basicDBObject) {
        this.basicDBObject = basicDBObject;
    }
}
