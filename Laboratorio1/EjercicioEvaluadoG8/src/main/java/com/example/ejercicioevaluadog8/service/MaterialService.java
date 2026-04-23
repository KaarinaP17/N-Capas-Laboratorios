package com.example.ejercicioevaluadog8.service;

import com.example.ejercicioevaluadog8.domain.model.Material;
import com.example.ejercicioevaluadog8.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {

        this.materialRepository = materialRepository;
    }

    public List<Material> findMaterialsByPriceDesc() {
        return materialRepository.findAll().stream()
                .sorted((p1, p2) -> Double.compare(p2.getPrecio(), p1.getPrecio()))
                .toList();
    }

    public Material getHighestPrice() {
        return materialRepository.findAll().stream()
                .max((m1, m2) -> Double.compare(m1.getPrecio(), m2.getPrecio()))
                .orElse(null);
    }

    public List<Material> legends() {
        return materialRepository.findAll().stream()
                .filter(m -> m.getRareza() == "Legendario")
                .toList();
    }

    public List<Material> uniqueLocations() {
        return materialRepository.findAll().stream()
                .map(material -> material.getUbicacion())
                .distinct()
                .map(ubicaciones ->
                     materialRepository.findAll().stream().filter(material -> material.getUbicacion() == ubicaciones)
                            .findFirst().get()).toList();
    }

    public List<Material> allMaterials(){

        return materialRepository.findAll();
    }
}
