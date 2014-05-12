package com.innerman.emotracker.model.device;

import com.google.common.base.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by petrpopov on 12.05.14.
 */
public class DartaSensorDTOManager {

    private static final String HEADER = "#DAR ";

    private static final String DATE_FORMAT_1 = "HH:mm:ss";
    private static final String DATE_FORMAT_2 = "HH:m:ss";

    private static final SimpleDateFormat DATE_FORMATTER_1 = new SimpleDateFormat(DATE_FORMAT_1);
    private static final SimpleDateFormat DATE_FORMATTER_2 = new SimpleDateFormat(DATE_FORMAT_2);

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
            res.setHeader( dto.getHeader() );
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

    public static boolean isValid(DartaSensorDTO dto) {

        if( dto.getDeviceDate() == null || dto.getSystemDate() == null || dto.getPulseMs() == 0 ) {
            return false;
        }

        return true;
    }
}
