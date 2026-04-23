package com.example.ejemploguiadag8;

import com.example.ejemploguiadag8.services.ProductServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EjemploGuiadaG8Application {

    public static void main(String[] args) {
        SpringApplication.run(EjemploGuiadaG8Application.class, args);
    }


    @Bean
    public CommandLineRunner run(ProductServices productServices) {

        return args -> {

            System.out.println("=== INICIANDO APP ===");
            productServices.findAll().forEach(p ->
                    System.out.println(p.getNombre() + " - $" + p.getPrecio())
            );
        };
    }
}
