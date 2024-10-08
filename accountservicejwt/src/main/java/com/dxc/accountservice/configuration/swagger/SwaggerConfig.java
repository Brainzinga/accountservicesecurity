package com.dxc.accountservice.configuration.swagger;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dxc.accountservice.controller.rest"))
                .paths(PathSelectors.any())
                .build()
                .consumes(Set.of(MediaType.APPLICATION_JSON_VALUE))
                .produces(Set.of(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Accountservice",
                "La API REST de accountservice App.",
                "v1",
                "Terms of service",
                new Contact("Grupo 4", "www.dxc.com", "myeaddress@company.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
