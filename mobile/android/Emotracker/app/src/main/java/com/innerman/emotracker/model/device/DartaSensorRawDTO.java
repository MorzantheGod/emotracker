package com.innerman.emotracker.model.device;

/**
 * Created by petrpopov on 12.04.14.
 */
public class DartaSensorRawDTO {

    private String header;
    private String counter;
    private String deviceDate;

    private String pulseMs;
    private String accX;
    private String accY;
    private String accZ;

    public static DartaSensorRawDTO fromString(String[] parts) {
        DartaSensorRawDTO raw = new DartaSensorRawDTO();

        if( parts.length != 7 ) {
            return raw;
        }

        raw.setHeader(parts[0]);
        raw.setCounter(parts[1]);
        raw.setDeviceDate(parts[2]);
        raw.setPulseMs(parts[3]);
        raw.setAccX(parts[4]);
        raw.setAccY(parts[5]);
        raw.setAccZ(parts[6]);

        return raw;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getDeviceDate() {
        return deviceDate;
    }

    public void setDeviceDate(String deviceDate) {
        this.deviceDate = deviceDate;
    }

    public String getPulseMs() {
        return pulseMs;
    }

    public void setPulseMs(String pulseMs) {
        this.pulseMs = pulseMs;
    }

    public String getAccX() {
        return accX;
    }

    public void setAccX(String accX) {
        this.accX = accX;
    }

    public String getAccY() {
        return accY;
    }

    public void setAccY(String accY) {
        this.accY = accY;
    }

    public String getAccZ() {
        return accZ;
    }

    public void setAccZ(String accZ) {
        this.accZ = accZ;
    }
}
