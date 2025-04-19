package com.senai.lecture.zero.from.job.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${openApi.url}")
    private String url;

    @Bean
    public OpenAPI myOpenAPI() {
        Server prodServer = new Server();
        prodServer.setUrl(url);
        prodServer.setDescription("Server URL in environment");

        Contact contact = new Contact();
        contact.setEmail("jackpot@contact.com");
        contact.setName("Jackpot Core Team");
        contact.setUrl("https://www.jackpot.com");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Jackpot Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes CRUD operations to manage Jackpot' users.").termsOfService("https://www.jackpot.com/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(prodServer));
    }
}
