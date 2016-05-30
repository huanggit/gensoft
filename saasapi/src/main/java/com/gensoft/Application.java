package com.gensoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.gensoft.saasapi.config.BaseProperties;


@SpringBootApplication
@EnableConfigurationProperties(BaseProperties.class)  
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
