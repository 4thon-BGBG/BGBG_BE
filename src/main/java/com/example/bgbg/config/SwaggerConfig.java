package com.example.bgbg.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("2025-4LINETHON-BGBE API") //이름
                .description("바로 구매하는 간편 장바구니, 바구바구"); //설명
        return new OpenAPI()
                .info(info);
    }

}