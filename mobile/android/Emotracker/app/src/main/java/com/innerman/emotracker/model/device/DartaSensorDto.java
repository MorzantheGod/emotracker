package com.innerman.emotracker.model.device;

import java.util.Date;

/**
 * Created by petrpopov on 12.04.14.
 */
public class DartaSensorDTO {

    private String header;
    private int counter;
    private Date deviceDate;
    private Date systemDate;

    private int pulseMs;
    private int accX;
    private int accY;
    private int accZ;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Date getDeviceDate() {
        return deviceDate;
    }

    public void setDeviceDate(Date deviceDate) {
        this.deviceDate = deviceDate;
    }

    public Date getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(Date systemDate) {
        this.systemDate = systemDate;
    }

    public int getPulseMs() {
        return pulseMs;
    }

    public void setPulseMs(int pulseMs) {
        this.pulseMs = pulseMs;
    }

    public int getAccX() {
        return accX;
    }

    public void setAccX(int accX) {
        this.accX = accX;
    }

    public int getAccY() {
        return accY;
    }

    public void setAccY(int accY) {
        this.accY = accY;
    }

    public int getAccZ() {
        return accZ;
    }

    public void setAccZ(int accZ) {
        this.accZ = accZ;
    }
}
