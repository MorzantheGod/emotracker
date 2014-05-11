package com.innerman.emotracker.core.service;

import com.innerman.emotracker.core.dto.DataEventDTO;
import com.innerman.emotracker.core.model.DataEventEntity;
import com.innerman.emotracker.core.model.UserEntity;
import com.innerman.emotracker.core.utils.EmoException;
import com.innerman.emotracker.core.utils.ErrorType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * User: petrpopov
 * Date: 07.05.14
 * Time: 1:08
 */

@Component
public class DataEventEntityService extends GenericEntityService<DataEventEntity> {

    @Autowired
    private UserEntityService userService;

    public DataEventEntityService() {
        super(DataEventEntity.class);
        logger = Logger.getLogger(DataEventEntityService.class);
    }

    public DataEventEntity saveDataForUser(DataEventDTO dto) throws EmoException {

        UserEntity user = userService.findById(dto.getUserId());
        if( user == null ) {
            throw new EmoException(ErrorType.no_such_user);
        }

        DataEventEntity data = new DataEventEntity();

        data.setUserId(user.getId());
        data.setName(dto.getName());
        data.setDescription(dto.getDescription());
        data.setStartDate(dto.getStartDate());
        data.setEndDate(dto.getEndDate());
        data.setSensors(dto.getSensors());

        return this.save(data);
    }

    public List<DataEventDTO> getLastEventsDTOs() {

        List<DataEventDTO> res = new ArrayList<DataEventDTO>();

        List<DataEventEntity> lastEvents = getLastEvents();
        for (DataEventEntity event : lastEvents) {
            DataEventDTO dto = DataEventDTO.fromDataEventEntity(event);
            res.add(dto);
        }

        return res;
    }

    public List<DataEventEntity> getLastEvents() {

        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "startDate"));

        return op.find(query, DataEventEntity.class);
    }
}
