package com.example.ejercicioevaluadog8.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Material {
    private String nombre;
    private String categoria;
    private String efecto;
    private Double precio;
    private String ubicacion;
    private String rareza;
}
