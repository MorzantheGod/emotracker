package com.innerman.emotracker.model.network;

import com.innerman.emotracker.model.device.DartaSensorDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by petrpopov on 09.05.14.
 */
public class DataEventDTO implements Serializable {

    private String id;

    private String userId;

    private String name;
    private String description;

    private Date startDate;
    private Date endDate;

    private List<DartaSensorDTO> sensors;

    private List<String> tags;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<DartaSensorDTO> getSensors() {
        return sensors;
    }

    public void setSensors(List<DartaSensorDTO> sensors) {
        this.sensors = sensors;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
