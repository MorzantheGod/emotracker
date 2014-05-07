package com.innerman.emotracker.core.model;

import com.innerman.emotracker.core.dto.DartaSensorDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User: petrpopov
 * Date: 07.05.14
 * Time: 0:59
 */

@Document(collection = "data_events")
public class DataEventEntity implements Serializable {

    @Id
    private String id;

    @Indexed
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

    public Date getStartDate() {
        return startDate;
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
