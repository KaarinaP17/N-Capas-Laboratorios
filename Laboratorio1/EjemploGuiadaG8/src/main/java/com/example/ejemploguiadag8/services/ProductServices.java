package com.example.ejemploguiadag8.services;

import com.example.ejemploguiadag8.domain.entity.Producto;
import com.example.ejemploguiadag8.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServices {
    private final ProductRepository productRepository;

    public List<Producto> findAll() {
        return productRepository.findAll();
    }
}
