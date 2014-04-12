package com.innerman.emotracker.bluetooth;

import com.google.common.base.Strings;
import com.innerman.emotracker.model.device.DartaSensorDTO;
import com.innerman.emotracker.model.device.DartaSensorRawDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrpopov on 12.04.14.
 */
public class DartaTextParser {

    private static final String HEADER = "#DAR ";

    public static List<DartaSensorDTO> fromString(String res) {

        List<DartaSensorDTO> list = new ArrayList<DartaSensorDTO>();
        if( Strings.isNullOrEmpty(res) ) {
            return list;
        }

        String[] strings = res.split("[\\r\\n]+");
        for (String s : strings) {
            if(Strings.isNullOrEmpty(s)) {
                continue;
            }

            if( s.length() < 20 ) {
                continue;
            }

            String header = s.substring(0, HEADER.length());
            if( !header.equals(HEADER)) {
                continue;
            }

            String other = s.substring(HEADER.length());
            if( Strings.isNullOrEmpty(other) ) {
                continue;
            }

            String[] split = other.split(",");

            String[] parts = new String[7];
            if( split.length < 6 ) {
                continue;
            }

            parts[0] = header;
            for(int i = 0; i < 6; i++) {
                parts[i+1] = split[i];
            }

            DartaSensorRawDTO raw = DartaSensorRawDTO.fromString(parts);

            DartaSensorDTO dto = DartaSensorDTO.fromRawDTO(raw);
            if( dto.isValid() ) {
                list.add(dto);
            }
        }

        return list;
    }
}
