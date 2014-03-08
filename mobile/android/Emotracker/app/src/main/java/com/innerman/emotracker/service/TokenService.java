package com.innerman.emotracker.service;

import com.innerman.emotracker.model.TokenMessage;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by petrpopov on 08.03.14.
 */
public class TokenService {

    private String API_URL = "http://euve35609.startvps.com:8080/emotracker-api/api/tokens/";

    public TokenMessage createToken() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        String url = API_URL + "create";

        TokenMessage res = restTemplate.postForObject(url, null, TokenMessage.class);
        return res;
    }
}
