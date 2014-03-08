package com.innerman.emotracker.service;

import com.innerman.emotracker.config.AppSettings;
import com.innerman.emotracker.model.MessageState;
import com.innerman.emotracker.model.WebMessage;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by petrpopov on 08.03.14.
 */
public abstract class ApiService<T> {

    private String BASE_API_URL;
    private Class<T> domainClass;

    public ApiService(Class<T> clazz) {
        this.domainClass = clazz;
    }

    protected T getForObject(String endpoint) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        String url = constructUrlForEndpoint(endpoint);


        try {
            T object = (T) restTemplate.getForObject(url, domainClass);
            return object;
        }
        catch (RestClientException e) {
            WebMessage res = new WebMessage();
            res.setState(MessageState.ERROR);
            res.setMessage("Server unavailable");

            return (T)res;
        }
    }

    protected T postForObject(String endpoint, Object requestBodyObject) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        String url = constructUrlForEndpoint(endpoint);


        try {
            T t = restTemplate.postForObject(url, requestBodyObject, domainClass);
            return t;
        }
        catch (RestClientException e) {
            WebMessage res = new WebMessage();
            res.setState(MessageState.ERROR);
            res.setMessage("Server unavailable");
            return (T)res;
        }
    }

    protected String constructUrlForEndpoint(String endpoint) {
        String url = getBaseApiUrl() + "/" + getCurrentApiUrl() + "/" + endpoint;
        return url;
    }

    protected abstract String getCurrentApiUrl();

    protected String getBaseApiUrl() {
        if( BASE_API_URL == null ) {
            BASE_API_URL = AppSettings.APP_SERVER_HOST + ":" + AppSettings.APP_SERVER_PORT + "/" + AppSettings.APP_SERVER_API;
        }

        return BASE_API_URL;
    }
}
