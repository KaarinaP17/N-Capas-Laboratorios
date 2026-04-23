package com.example.ejercicioevaluadog8;

import com.example.ejercicioevaluadog8.common.PrintMaterial;
import com.example.ejercicioevaluadog8.service.MaterialService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EjercicioEvaluadoG8Application {
    private final MaterialService service;
    private final PrintMaterial printer;

    public EjercicioEvaluadoG8Application(MaterialService service, PrintMaterial printer) {
        this.service = service;
        this.printer = printer;
    }


    public static void main(String[] args) {
        SpringApplication.run(EjercicioEvaluadoG8Application.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println("Material de mayor a menor");
            printer.imprimir(service.findMaterialsByPriceDesc());
            System.out.println("Material mas caro");
            printer.imprimir(service.getHighestPrice());
            System.out.println("Materiales con grado de rareza Legendario");
            printer.imprimir(service.legends());
            System.out.println("Material de lista registradas usando Distinct");
            printer.imprimir(service.uniqueLocations());
        };
    }

}
