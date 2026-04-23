package com.example.ejercicioevaluadog8.common;

import com.example.ejercicioevaluadog8.domain.model.Material;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrintMaterial {

    public void imprimir(List<Material> materials) {
        materials.forEach(m ->
                System.out.println(
                        "[HYRULE-DB] Nombre: " + m.getNombre() +
                                " | Categoría: " + m.getCategoria() +
                                " | Precio: " + m.getPrecio() + " Rupias"
                ));
    }

    public void imprimir(Material m) {
        System.out.println(
                "[HYRULE-DB] Nombre: " + m.getNombre() +
                        " | Categoría: " + m.getCategoria() +
                        " | Precio: " + m.getPrecio() + " Rupias"
        );

    }
}