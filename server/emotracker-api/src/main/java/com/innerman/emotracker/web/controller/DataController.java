package com.innerman.emotracker.web.controller;

import com.innerman.emotracker.core.dto.DartaSensorDTO;
import com.innerman.emotracker.core.dto.DataEventDTO;
import com.innerman.emotracker.core.dto.UserDataDTO;
import com.innerman.emotracker.core.model.DataEventEntity;
import com.innerman.emotracker.core.service.DataEntityService;
import com.innerman.emotracker.core.service.DataEventEntityService;
import com.innerman.emotracker.core.utils.EmoException;
import com.innerman.emotracker.web.data.WebMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by petrpopov on 11.03.14.
 */

@Controller("dataWebController")
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private DataEntityService dataEntityService;

    @Autowired
    private DataEventEntityService dataEventEntityService;

    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    @ResponseBody
    public Object saveData(@Valid @RequestBody UserDataDTO data, BindingResult result) {

        if( result.hasErrors() ) {
            return WebMessage.createValidationError();
        }

        try {
            dataEntityService.saveDataForUser(data);
        }
        catch (EmoException e) {
            return WebMessage.createError(e);
        }

        return WebMessage.createOK("OK");
    }

    @RequestMapping(value = "/getDataEvents.data", method = RequestMethod.GET)
    @ResponseBody
    public Object getDataEvents() {

        List<DataEventDTO> lastEvents = dataEventEntityService.getLastEventsDTOs();

        DataEventDTO dto = new DataEventDTO();
        dto.setId("af72397sdf");
        dto.setUserId("dshfkjh2832");
        dto.setStartDate(new Date());
        dto.setEndDate(new Date());
        dto.setName("Фильм Матрица");

        lastEvents.add(dto);

        return lastEvents;
    }

    @RequestMapping(value = "/getDataEvent.data", method = RequestMethod.GET)
    @ResponseBody
    public Object getDataEvent(@RequestParam String id) {

        DataEventEntity res = new DataEventEntity();
        res.setId(id);
        res.setName("Фильм Матрица");
        res.setDescription("Просмотр фильма с приложением");
        res.setStartDate(new Date());

        List<DartaSensorDTO> list = new ArrayList<DartaSensorDTO>();

        DartaSensorDTO dto = new DartaSensorDTO();
        dto.setHeader("#DAR");
        dto.setPulseMs(230);
        dto.setCounter(0);
        dto.setDeviceDate(new Date());
        dto.setAccY(3);
        dto.setAccX(4);
        dto.setAccZ(5);
        list.add(dto);

        res.setSensors(list);

        return res;
        //return dataEventEntityService.findById(id);
    }
}
