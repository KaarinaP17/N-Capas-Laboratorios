package com.example.ejerciciopiratas.controller;

import com.example.ejerciciopiratas.domain.entity.Pirate;
import com.example.ejerciciopiratas.service.PirateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pirates")
@RequiredArgsConstructor
public class PirateController {

    private final PirateService pirateService;

    // Crear pirata (POST)
    @PostMapping
    public ResponseEntity<Pirate> createPirate(@RequestBody Pirate pirate) {
        Pirate saved = pirateService.createPirate(pirate);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Obtener todos (GET)
    @GetMapping
    public ResponseEntity<List<Pirate>> getAllPirates() {
        return ResponseEntity.ok(pirateService.getAllPirates());
    }

    // Obtener por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Pirate> getPirateById(@PathVariable UUID id) {
        return ResponseEntity.ok(pirateService.getPirateById(id));
    }

    // Actualizar pirata (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Pirate> updatePirate(@PathVariable UUID id, @RequestBody Pirate pirate) {
        Pirate updated = pirateService.updatePirate(id, pirate);
        return ResponseEntity.ok(updated);
    }

    // Eliminar pirata (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePirate(@PathVariable UUID id) {
        pirateService.deletePirate(id);
        return ResponseEntity.noContent().build();
    }
}