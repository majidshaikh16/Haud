package com.haud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configure swagger for api documentation.
 *
 * @author webwerks
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * Configure the api package to be scanned.
     *
     * @return {@link Docket}
     */

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.haud.api"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Describe your apis common details
     *
     * @return {@link ApiInfo}
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("Neosoft", "http://neosofttech.com", "email@neosofttech.com");
        return new ApiInfoBuilder()
                .title("Haud")
                .description("This page lists all the rest apis for Haud")
                .version("1.0.0")
                .license("Neosoft pvt ltd v1.0.")
                .contact(contact)
                .build();
    }
}
