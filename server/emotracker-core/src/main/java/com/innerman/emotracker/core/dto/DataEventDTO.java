package com.innerman.emotracker.core.dto;

import com.innerman.emotracker.core.model.DataEventEntity;

import java.util.Date;
import java.util.List;

/**
 * User: petrpopov
 * Date: 08.05.14
 * Time: 23:50
 */

public class DataEventDTO {

    private String id;

    private String userId;

    private String name;
    private String description;

    private Date startDate;
    private Date endDate;

    private List<DartaSensorDTO> sensors;
    private List<String> tags;

    private String uuid;

    public static DataEventDTO fromDataEventEntity(DataEventEntity entity) {

        if( entity == null ) {
            return null;
        }

        DataEventDTO res = new DataEventDTO();

        res.setId(entity.getId());
        res.setUserId(entity.getUserId());
        res.setName(entity.getName());
        res.setDescription(entity.getDescription());
        res.setStartDate(entity.getStartDate());
        res.setEndDate(entity.getEndDate());

        return res;
    }

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
