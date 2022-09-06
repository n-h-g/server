package com.nhg.itemeditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ItemEditorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemEditorApplication.class, args);
    }
}
