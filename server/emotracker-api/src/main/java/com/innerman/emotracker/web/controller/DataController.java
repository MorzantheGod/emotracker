package com.innerman.emotracker.web.controller;

import com.innerman.emotracker.core.dto.DartaSensorDTO;
import com.innerman.emotracker.core.dto.DataEventDTO;
import com.innerman.emotracker.core.dto.UserDataDTO;
import com.innerman.emotracker.core.model.DataEventEntity;
import com.innerman.emotracker.core.model.UserEntity;
import com.innerman.emotracker.core.service.DataEntityService;
import com.innerman.emotracker.core.service.DataEventEntityService;
import com.innerman.emotracker.core.utils.EmoException;
import com.innerman.emotracker.web.data.WebMessage;
import com.innerman.emotracker.web.utils.UserContextHandler;
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
    private UserContextHandler userContextHandler;

    @Autowired
    private DataEntityService dataEntityService;

    @Autowired
    private DataEventEntityService dataEventEntityService;

    @RequestMapping(value = "/saveDataEvent", method = RequestMethod.POST)
    @ResponseBody
    public Object saveDataEvent(@Valid @RequestBody DataEventDTO data, BindingResult result) {

        if( result.hasErrors() ) {
            return WebMessage.createValidationError();
        }

        try {
            dataEventEntityService.saveDataForUser(data);
        }
        catch (EmoException e) {
            return WebMessage.createError(e);
        }

        return WebMessage.createOK("OK");
    }

    @Deprecated
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

        List<DataEventDTO> res = new ArrayList<DataEventDTO>();

        UserEntity user = userContextHandler.currentContextUser();
        if( user == null ) {
            return res;
        }

        res = dataEventEntityService.getLastEventsDTOs(user);

        DataEventDTO dto = new DataEventDTO();
        dto.setId("536c93c83004979c703761c6");
        dto.setUserId("536c93c83004979c703761c6");
        dto.setStartDate(new Date());
        dto.setEndDate(new Date());
        dto.setName("Фильм Матрица");
        dto.setDescription("Просмотр фильма с приложением Emotracker mobile");

        res.add(dto);

        return res;
    }

    @RequestMapping(value = "/getDataEvent.data", method = RequestMethod.GET)
    @ResponseBody
    public Object getDataEvent(@RequestParam String id) {

        DataEventEntity res = new DataEventEntity();

        UserEntity user = userContextHandler.currentContextUser();
        if( user == null ) {
            return res;
        }

        res = dataEventEntityService.getDataEventForUser(id, user);
        res = new DataEventEntity();

        res.setId(id);
        res.setName("Фильм Матрица");
        res.setDescription("Просмотр фильма с приложением Emotracker mobile");
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

        DartaSensorDTO dto1 = new DartaSensorDTO();
        dto1.setHeader("#DAR");
        dto1.setPulseMs(244);
        dto1.setCounter(1);
        dto1.setDeviceDate(new Date());
        dto1.setAccY(4);
        dto1.setAccX(2);
        dto1.setAccZ(4);
        list.add(dto1);

        DartaSensorDTO dto2 = new DartaSensorDTO();
        dto2.setHeader("#DAR");
        dto2.setPulseMs(210);
        dto2.setCounter(2);
        dto2.setDeviceDate(new Date());
        dto2.setAccY(1);
        dto2.setAccX(2);
        dto2.setAccZ(5);
        list.add(dto2);

        DartaSensorDTO dto3 = new DartaSensorDTO();
        dto3.setHeader("#DAR");
        dto3.setPulseMs(252);
        dto3.setCounter(3);
        dto3.setDeviceDate(new Date());
        dto3.setAccY(7);
        dto3.setAccX(4);
        dto3.setAccZ(6);
        list.add(dto3);

        DartaSensorDTO dto4 = new DartaSensorDTO();
        dto4.setHeader("#DAR");
        dto4.setPulseMs(211);
        dto4.setCounter(4);
        dto4.setDeviceDate(new Date());
        dto4.setAccY(9);
        dto4.setAccX(4);
        dto4.setAccZ(9);
        list.add(dto4);

        res.setSensors(list);

        return res;
    }
}
