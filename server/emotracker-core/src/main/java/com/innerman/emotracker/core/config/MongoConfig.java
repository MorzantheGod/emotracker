package com.innerman.emotracker.core.config;

import com.mongodb.MongoClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.net.UnknownHostException;


/**
 * User: petrpopov
 * Date: 12.02.13
 * Time: 12:57
 */

@Configuration
public class MongoConfig {

    @Value("${mongodb_username}")
    private String MONGODB_USERNAME;
    @Value("${mongodb_password}")
    private String MONGODB_PASSWORD;
    @Value("${mongodb_host}")
    private String MONGODB_HOST;
    @Value("${mongodb_port}")
    private String MONGODB_PORT;
    @Value("${mongodb_db}")
    private String MONGODB_DB;

    public @Bean
    UserCredentials userCredentials() throws UnknownHostException {

        return new UserCredentials(MONGODB_USERNAME, MONGODB_PASSWORD);
    }

    public @Bean
    MongoDbFactory mongoDbFactory() throws Exception {
        log().info("Creating MongoDB factory");

        //Mongo mongo = new Mongo( MONGODB_HOST );
        MongoClient mongoClient = new MongoClient( MONGODB_HOST, Integer.parseInt(MONGODB_PORT) );
        return new SimpleMongoDbFactory(mongoClient, MONGODB_DB, userCredentials());
    }

    public @Bean
    MongoTemplate mongoTemplate() throws Exception {
        log().info("Creating MongoTemplate");

        MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory(),
                new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), converter);
        return mongoTemplate;
        //return new MongoTemplate(mongoDbFactory());
    }

    private Logger log()
    {
        return Logger.getLogger(MongoConfig.class);
    }
}
