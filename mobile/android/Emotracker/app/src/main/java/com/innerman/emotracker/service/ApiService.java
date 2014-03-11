package com.innerman.emotracker.service;

import com.innerman.emotracker.config.AppSettings;
import com.innerman.emotracker.model.MessageState;
import com.innerman.emotracker.model.WebMessage;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by petrpopov on 08.03.14.
 */
public abstract class ApiService<T> {

    private String BASE_API_URL;
    private Class<T> domainClass;

    private static final String UNAVAILABLE = "Server unavailable";

    public ApiService(Class<T> clazz) {
        this.domainClass = clazz;
    }

    protected WebMessage<T> getForObject(String endpoint) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        String url = constructUrlForEndpoint(endpoint);


        try {
            WebMessage message = restTemplate.getForObject(url, WebMessage.class);
            return getConvertedResult(message);
        }
        catch (RestClientException e) {
            return getErrorResult();
        }
    }

    protected WebMessage<T> postForObject(String endpoint, Object requestBodyObject) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        String url = constructUrlForEndpoint(endpoint);


        try {
            WebMessage message = restTemplate.postForObject(url, requestBodyObject, WebMessage.class);
            return getConvertedResult(message);
        }
        catch (RestClientException e) {
            return getErrorResult();
        }
    }

    protected WebMessage<T> getConvertedResult(WebMessage message) {
        WebMessage<T> res = new WebMessage<T>();
        res.setMessage(message.getMessage());
        res.setState(message.getState());

        if( message.getResult() != null ) {

            try {
                ObjectMapper mapper = new ObjectMapper();
                T t = mapper.convertValue(message.getResult(), domainClass);
                res.setResult(t);
            }
            catch (Exception e) {}

        }
        return res;
    }

    protected WebMessage<T> getErrorResult() {
        WebMessage<T> res = new WebMessage<T>();
        res.setState(MessageState.ERROR);
        res.setMessage(UNAVAILABLE);

        return res;
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
