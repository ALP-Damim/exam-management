package com.kt.damim.exammanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "Exam Management API", version = "v1", description = "시험/문항 조회 API"),
    servers = {
        @Server(url = "/", description = "Default")
    }
)
@Configuration
public class OpenApiConfig {}


