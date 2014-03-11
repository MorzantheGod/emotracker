package com.innerman.emotracker.dto;

import java.util.Date;

/**
 * Created by petrpopov on 11.03.14.
 */
public class SensorDTO {

    private int heartRate;
    private long time;
    private Date date;

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "heartRate=" + heartRate + ", time=" + time + ", date=" + date;
    }
}
