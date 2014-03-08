package com.innerman.emotracker.service;

import com.innerman.emotracker.model.RegistrationDTO;
import com.innerman.emotracker.model.WebMessage;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by petrpopov on 08.03.14.
 */
public class UserService {

    private String API_URL = "http://euve35609.startvps.com:8080/emotracker-api/api/users/";

    public WebMessage testUser() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        String url = API_URL + "test";

        WebMessage res = restTemplate.getForObject(url, WebMessage.class);
        return res;
    }

    public WebMessage signUpUser(RegistrationDTO dto) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        String url = API_URL + "create";

        WebMessage res = restTemplate.postForObject(url, dto, WebMessage.class);
        return res;
    }
}
