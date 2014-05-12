package com.innerman.emotracker.core.dto;

import java.util.Date;
import java.util.List;

/**
 * User: petrpopov
 * Date: 12.05.14
 * Time: 21:59
 */
public class TestDTO {


    private String userId;
    private String name;
    private String description;

    private Date startDate;
    private Date endDate;

    private List<String> tags;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
