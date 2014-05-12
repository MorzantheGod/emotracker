package com.innerman.emotracker.core.service;

import com.innerman.emotracker.core.dto.DataEventDTO;
import com.innerman.emotracker.core.model.DataEventEntity;
import com.innerman.emotracker.core.model.UserEntity;
import com.innerman.emotracker.core.utils.EmoException;
import com.innerman.emotracker.core.utils.ErrorType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public DataEventEntity getDataEventForUser(String id, UserEntity user) {

//        Query query = new Query();
//
//        Criteria criteria = new Criteria();
//        criteria.andOperator(Criteria.where("userId").is(user.getId()), Criteria.where("id").is(new ObjectId(id)));
//        query.addCriteria(criteria);
//
//        List<DataEventEntity> list = op.find(query, DataEventEntity.class);
//        if( list == null || list.isEmpty() ) {
//            return null;
//        }
//
//        return list.get(0);

        DataEventEntity data = op.findById(id, DataEventEntity.class);
        if( data != null ) {
            if( !data.getUserId().equals(user.getId())) {
                return null;
            }
        }

        if( data.getSensors() != null ) {
            Collections.sort(data.getSensors());
        }

        return data;
    }

//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
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

//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//    @PreAuthorize("(hasRole('ROLE_USER') and #user.id==principal.username) or hasRole('ROLE_ADMIN')")
    public List<DataEventDTO> getLastEventsDTOs(UserEntity user) {

        List<DataEventDTO> res = new ArrayList<DataEventDTO>();

        List<DataEventEntity> lastEvents = getLastEvents(user);
        for (DataEventEntity event : lastEvents) {
            DataEventDTO dto = DataEventDTO.fromDataEventEntity(event);
            res.add(dto);
        }

        return res;
    }

    private List<DataEventEntity> getLastEvents(UserEntity user) {

        Criteria crit = Criteria.where("userId").is(user.getId());
        Query query = new Query(crit);
        query.with(new Sort(Sort.Direction.DESC, "startDate"));

        return op.find(query, DataEventEntity.class);
    }
}
