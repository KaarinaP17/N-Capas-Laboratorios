package com.example.ejerciciopiratas.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "pirates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pirate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private Double bounty;

    private String crew;

    private Boolean isAlive;
}