package com.c9Pay.userservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerApiConfig {
    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .version("0.1")
                .title("사용자 서비스 API")
                .description("사용자 서비스 API 명세서");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
