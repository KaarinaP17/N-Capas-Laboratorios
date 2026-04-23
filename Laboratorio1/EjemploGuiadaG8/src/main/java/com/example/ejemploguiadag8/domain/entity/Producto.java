package com.example.ejemploguiadag8.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //instancias de clases
@AllArgsConstructor //consructor con todos los productos
@NoArgsConstructor //constructor vacio
public class Producto {
    private String nombre;
    private Long id;
    private Double precio;
}
