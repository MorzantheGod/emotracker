package com.innerman.emotracker.model;

/**
 * Created by petrpopov on 10.03.14.
 */
public class SensorDTO {
    private int heartRate;
    private long time;

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

    @Override
    public String toString() {
        return "heartRate=" + heartRate + ", time=" + time;
    }
}
