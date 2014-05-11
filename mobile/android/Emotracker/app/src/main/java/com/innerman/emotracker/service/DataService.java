package com.innerman.emotracker.service;

import com.innerman.emotracker.model.network.DataEventDTO;
import com.innerman.emotracker.model.network.WebMessage;

/**
 * Created by petrpopov on 11.03.14.
 */
public class DataService extends ApiService<DataEventDTO> {

    private final static String API_URL = "data";
//    private final static String SAVE_DATA = "saveData";
    private final static String SAVE_DATA_EVENT = "saveDataEvent";

    public DataService() {
        super(DataEventDTO.class);
    }

    public WebMessage<DataEventDTO> saveDataEvent(DataEventDTO dto) {

        WebMessage<DataEventDTO> data = this.postForObject(SAVE_DATA_EVENT, dto);

        return data;
    }

//    @Deprecated
//    public WebMessage<DataDTO> saveData(DataDTO dto) {
//        WebMessage<DataDTO> saveData = this.postForObject(SAVE_DATA, dto);
//
//        return saveData;
//    }

    @Override
    protected String getCurrentApiUrl() {
        return API_URL;
    }
}
