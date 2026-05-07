package com.example.ejerciciopiratas.service.impl;

import com.example.ejerciciopiratas.domain.entity.Pirate;
import com.example.ejerciciopiratas.repository.PirateRepository;
import com.example.ejerciciopiratas.service.PirateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PirateServiceImpl implements PirateService {

    private final PirateRepository pirateRepository;

    @Override
    public Pirate createPirate(Pirate pirate) {
        return pirateRepository.save(pirate);
    }

    @Override
    public List<Pirate> getAllPirates() {
        return pirateRepository.findAll();
    }

    @Override
    public Pirate getPirateById(UUID id) {
        return pirateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pirata no encontrado con ID: " + id));
    }

    @Override
    public Pirate updatePirate(UUID id, Pirate pirate) {
        Pirate existingPirate = getPirateById(id);
        existingPirate.setName(pirate.getName());
        existingPirate.setBounty(pirate.getBounty());
        existingPirate.setCrew(pirate.getCrew());
        existingPirate.setIsAlive(pirate.getIsAlive());
        return pirateRepository.save(existingPirate);
    }

    @Override
    public void deletePirate(UUID id) {
        Pirate pirate = getPirateById(id);
        pirateRepository.deleteById(id);
    }
}
