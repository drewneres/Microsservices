package org.compass.desafio2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicrosservicoAApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicrosservicoAApplication.class, args);
    }
}