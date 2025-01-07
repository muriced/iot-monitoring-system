package io.github.muriced.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("IoT Monitoring API")
                        .version("1.0")
                        .description("API para monitoramento de dispositivos IoT")
                        .contact(new Contact()
                                .name("Murilo Cedraz")
                                .email("daucedraz@gmail.com")));
    }
}