package com.innerman.emotracker.service;

import com.innerman.emotracker.model.DataDTO;
import com.innerman.emotracker.model.WebMessage;

/**
 * Created by petrpopov on 11.03.14.
 */
public class DataService extends ApiService<DataDTO> {

    private final static String API_URL = "data";
    private final static String SAVE_DATA = "saveData";

    public DataService() {
        super(DataDTO.class);
    }

    public WebMessage<DataDTO> saveData(DataDTO dto) {
        WebMessage<DataDTO> saveData = this.postForObject(SAVE_DATA, dto);

        return saveData;
    }

    @Override
    protected String getCurrentApiUrl() {
        return API_URL;
    }
}
