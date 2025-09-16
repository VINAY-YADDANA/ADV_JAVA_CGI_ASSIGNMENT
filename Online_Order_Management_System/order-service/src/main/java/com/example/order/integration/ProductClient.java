package com.example.order.integration;

import com.example.order.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", path = "/api/products")
public interface ProductClient {

    @GetMapping("/{id}")
    ProductDto getProduct(@PathVariable("id") Long id);

    @PostMapping("/{id}/reserve")
    void reserve(@PathVariable("id") Long id, @RequestParam("qty") int qty);

    @PostMapping("/{id}/restock")
    void restock(@PathVariable("id") Long id, @RequestParam("qty") int qty);
}
