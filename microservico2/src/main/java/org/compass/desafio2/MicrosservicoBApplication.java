package org.compass.desafio2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicrosservicoBApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicrosservicoBApplication.class, args);
    }
}
