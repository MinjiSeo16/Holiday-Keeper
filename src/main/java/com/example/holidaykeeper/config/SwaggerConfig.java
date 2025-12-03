package com.example.holidaykeeper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Holiday Keeper API")
				.description("플랜잇스퀘어 과제 - 공휴일 관리 API")
				.version("v1.0"));
	}
}
