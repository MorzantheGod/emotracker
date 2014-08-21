package com.innerman.emotracker.service;

import com.innerman.emotracker.model.network.DataEventDTO;
import com.innerman.emotracker.model.network.WebMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrpopov on 11.03.14.
 */
public class DataService extends ApiService<DataEventDTO> {

    private final static String API_URL = "data";
    private final static String SAVE_DATA_EVENT = "saveDataEvent";

    public DataService() {
        super(DataEventDTO.class);
    }

    public List<WebMessage<DataEventDTO>> saveDataEvents(List<DataEventDTO> list) {

        List<WebMessage<DataEventDTO>> res = new ArrayList<WebMessage<DataEventDTO>>();
        for (DataEventDTO dto : list) {
            res.add(saveDataEvent(dto));
        }

        return res;
    }

    public WebMessage<DataEventDTO> saveDataEvent(DataEventDTO dto) {

        WebMessage<DataEventDTO> web = this.postForObject(SAVE_DATA_EVENT, dto);
        if( web.getResult() == null ) {
            web.setResult(dto);
        }
        else {
//            web.getResult().setUuid(dto.getUuid());
        }

        return web;
    }

    @Override
    protected String getCurrentApiUrl() {
        return API_URL;
    }
}
