package org.compass.desafio2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients  // Habilita o Feign
@SpringBootApplication
public class Desafio2Application {
    public static void main(String[] args) {
        SpringApplication.run(Desafio2Application.class, args);
    }
}
