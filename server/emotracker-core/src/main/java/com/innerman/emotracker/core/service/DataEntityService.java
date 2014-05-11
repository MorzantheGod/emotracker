package com.innerman.emotracker.core.service;

import com.innerman.emotracker.core.dto.SensorDTO;
import com.innerman.emotracker.core.dto.UserDataDTO;
import com.innerman.emotracker.core.model.DataEntity;
import com.innerman.emotracker.core.model.UserEntity;
import com.innerman.emotracker.core.utils.EmoException;
import com.innerman.emotracker.core.utils.ErrorType;
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
@Deprecated
public class DataEntityService extends GenericEntityService<DataEntity> {

    @Autowired
    private UserEntityService userService;

    public DataEntityService() {
        super(DataEntity.class);
        logger = Logger.getLogger(DataEntityService.class);
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
