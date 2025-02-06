package org.compass.desafio2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Desafio 2 API - Grupo 4")
                                .description("API baseada em microsserviços que consumirá dados da API JSONPlaceholder")
                                .version("v1")
                                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                                //.contact(new Contact().name("Juan Oliveira").email("juan.oliveira.pb@compasso.com.br"))
                                //.contact(new Contact().name("Hendrew Queiroz").email("hendrew.queiroz.pb@compasso.com.br"))
                                //.contact(new Contact().name("Matheus Guerra").email("matheus.guerra.pb@compasso.com.br"))
                                //.contact(new Contact().name("Paulo Dias").email("paulo.dias.pb@compasso.com.br"))
                );
    }
}
