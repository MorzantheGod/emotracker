package com.innerman.emotracker.service;

import com.innerman.emotracker.dto.SensorDTO;
import com.innerman.emotracker.dto.UserDataDTO;
import com.innerman.emotracker.model.DataEntity;
import com.innerman.emotracker.model.UserEntity;
import com.innerman.emotracker.utils.EmoException;
import com.innerman.emotracker.utils.ErrorType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by petrpopov on 11.03.14.
 */

@Component
public class DataService extends GenericService<DataEntity> {

    @Autowired
    private UserService userService;

    public DataService() {
        super(DataEntity.class);
        logger = Logger.getLogger(DataService.class);
    }

    public void saveDataForUser(UserDataDTO data) throws EmoException {

        UserEntity user = userService.getUserByUsername(data.getUsername());
        if( user == null ) {
            throw new EmoException(ErrorType.no_such_user);
        }

        DataEntity save = new DataEntity();
        save.setUserId(user.getId());
        save.setEventDate(new Date());

        List<SensorDTO> sensors = new ArrayList<SensorDTO>();
        save.setSensors(sensors);

        for (SensorDTO sensorDTO : data.getData()) {
            sensors.add(sensorDTO);
        }

        this.save(save);
    }
}
