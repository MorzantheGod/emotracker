package com.innerman.emotracker.core.service;

import com.innerman.emotracker.core.model.DataEventEntity;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: petrpopov
 * Date: 07.05.14
 * Time: 1:08
 */

@Component
public class DataEventEntityService extends GenericEntityService<DataEventEntity> {

    public DataEventEntityService() {
        super(DataEventEntity.class);
        logger = Logger.getLogger(DataEventEntityService.class);
    }


    public List<DataEventEntity> getLastEvents() {

        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "startDate"));

        return op.find(query, DataEventEntity.class);
    }
}
