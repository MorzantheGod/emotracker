package com.innerman.emotracker.model.device;

import com.google.common.base.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by petrpopov on 12.04.14.
 */
public class DartaSensorDTO {

    private static final String HEADER = "#DAR ";

    private static final String DATE_FORMAT_1 = "HH:mm:ss";
    private static final String DATE_FORMAT_2 = "HH:m:ss";

    private static final SimpleDateFormat DATE_FORMATTER_1 = new SimpleDateFormat(DATE_FORMAT_1);
    private static final SimpleDateFormat DATE_FORMATTER_2 = new SimpleDateFormat(DATE_FORMAT_2);

    private int counter;
    private Date deviceDate;
    private Date systemDate;

    private int pulseMs;
    private int accX;
    private int accY;
    private int accZ;

    public static DartaSensorDTO fromRawDTO(DartaSensorRawDTO dto) {

        DartaSensorDTO res = new DartaSensorDTO();
        if( dto == null ) {
            return res;
        }

        if( Strings.isNullOrEmpty(dto.getHeader())) {
            return res;
        }

        if( !dto.getHeader().equals(HEADER)) {
            return res;
        }


        //convert message
        try {
            res.setCounter( Integer.parseInt(dto.getCounter()) );
            res.setPulseMs( Integer.parseInt(dto.getPulseMs()) );
            res.setAccX( Integer.parseInt(dto.getAccX()) );
            res.setAccY( Integer.parseInt(dto.getAccY()) );
            res.setAccZ( Integer.parseInt(dto.getAccZ()) );

            Date parse = null;
            try {
                parse = DATE_FORMATTER_1.parse(dto.getDeviceDate());
            }
            catch (ParseException e) {
                try {
                    parse = DATE_FORMATTER_2.parse(dto.getDeviceDate());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            res.setDeviceDate(parse);
            res.setSystemDate(new Date());
        }
        catch(NumberFormatException e) {

        }

        return res;
    }

    public boolean isValid() {

        if( deviceDate == null || systemDate == null || pulseMs == 0 ) {
            return false;
        }

        return true;
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
