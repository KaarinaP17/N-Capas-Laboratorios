package com.example.ejemploguiadag8.repository;

import com.example.ejemploguiadag8.common.ProductList;
import com.example.ejemploguiadag8.domain.entity.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final ProductList productList;

    public List<Producto> findAll(){
        return productList.getProducts();
    }
}
