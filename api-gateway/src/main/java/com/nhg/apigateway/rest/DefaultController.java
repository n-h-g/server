package com.nhg.apigateway.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;

@RestController
public class DefaultController {

    @GetMapping("/")
    public Collection<String> index() {
        return Arrays.asList(
                "http://localhost:8080/account/swagger-ui/index.html",

                "http://localhost:8080/catalogue/swagger-ui/index.html",

                "http://localhost:8080/game/swagger-ui/index.html",

                "http://localhost:8080/messenger/swagger-ui/index.html",

                "http://localhost:8080/moderation/swagger-ui/index.html",

                "http://localhost:8080/actuator"
        );
    }

}