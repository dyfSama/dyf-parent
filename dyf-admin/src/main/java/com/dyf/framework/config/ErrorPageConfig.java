package com.dyf.framework.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * <p>
 * todo
 * </p>
 *
 * @auther: duyafei
 * @date: 2019/1/30 20:58
 */
@Configuration
public class ErrorPageConfig {
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error-400");
            ErrorPage errorPage403 = new ErrorPage(HttpStatus.FORBIDDEN, "/error-403");
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-404");
            ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-500");
            factory.addErrorPages(errorPage400, errorPage404, errorPage500, errorPage403);
        };
    }

}
