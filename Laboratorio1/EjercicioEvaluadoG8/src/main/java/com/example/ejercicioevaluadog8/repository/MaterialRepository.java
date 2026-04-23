package com.example.ejercicioevaluadog8.repository;

import com.example.ejercicioevaluadog8.common.MaterialList;
import com.example.ejercicioevaluadog8.domain.model.Material;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MaterialRepository {
    private final MaterialList materials;

    public MaterialRepository(MaterialList materials) {

        this.materials = materials;
    }

    public List<Material> findAll() {

        return materials.getMateriales();
    }
}
