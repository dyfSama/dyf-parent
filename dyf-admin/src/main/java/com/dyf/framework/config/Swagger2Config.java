package com.dyf.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * <p>
 * swagger2配置
 * </p>
 *
 * @auther: duyafei
 * @date: 2019/1/31 10:17
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("dyfSama--接口文档")
                .description("舒服!")
                .termsOfServiceUrl("http://localhost:8080")
                .contact(new Contact("dyfSama", "https://github.com/dyfSama", "du_yafei@163.com"))
                .license("")
                .licenseUrl("")
                .version("1.0.0")
                .build();
    }
}

