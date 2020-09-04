package com.springboot.myspringboot.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

public class ErrorPageOperate {

    @Bean
    public WebServerFactoryCustomizer containerCustomizer() {

        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/static/index.html");

        return (WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>) factory -> {
//                factory.setPort(8090);
            factory.addErrorPages(error404Page);
        };
    }
}