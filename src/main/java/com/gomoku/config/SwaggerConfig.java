package com.gomoku.config;

import static springfox.documentation.builders.PathSelectors.ant;
import static springfox.documentation.builders.RequestHandlerSelectors.any;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration for swagger (springfox).
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String API_PATH = "/api/**";

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(any())
                .paths(ant(API_PATH))
                .build();
    }

}