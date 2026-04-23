package com.example.ejercicioevaluadog8.common;

import com.example.ejercicioevaluadog8.domain.model.Material;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class MaterialList {
    private final List<Material> materiales;

    public MaterialList() {
        this.materiales = new ArrayList<>();

        materiales.add(Material.builder()
                .nombre("Ámbar Rojo")
                .categoria("Mineral")
                .efecto("Defensa")
                .precio(30.0)
                .ubicacion("Cordillera de Hebra")
                .rareza("Común")
                .build());

        materiales.add(Material.builder()
                .nombre("Ala de Keese")
                .categoria("Parte de Monstruo")
                .efecto("Sigilo")
                .precio(15.0)
                .ubicacion("Cuevas")
                .rareza("Poco Común")
                .build());

        materiales.add(Material.builder()
                .nombre("Pimienta Ardiente")
                .categoria("Planta")
                .efecto("Ataque")
                .precio(10.0)
                .ubicacion("Volcán de Eldin")
                .rareza("Común")
                .build());

        materiales.add(Material.builder()
                .nombre("Trufa Gigante")
                .categoria("Comida")
                .efecto("Corazones")
                .precio(100.0)
                .ubicacion("Bosque")
                .rareza("Raro")
                .build());

        materiales.add(Material.builder()
                .nombre("Escama de Dragón")
                .categoria("Parte de Monstruo")
                .efecto("Estamina")
                .precio(500.0)
                .ubicacion("Lago Hylia")
                .rareza("Legendario")
                .build());
    }

}
