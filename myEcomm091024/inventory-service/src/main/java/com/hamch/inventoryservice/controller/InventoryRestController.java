package com.hamch.inventoryservice.controller;

import com.hamch.inventoryservice.model.Inventory;
import com.hamch.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class InventoryRestController {
    private final InventoryRepository inventoryRepository;
    @GetMapping("/inventory/{skuCode}")
    Boolean isInStock(@PathVariable String skuCode) {
        System.out.println("TTTTTTTTTTTTT");
        log.info("Checking stock for product with skucode - " + skuCode);
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new RuntimeException("Cannot Find Product by sku code " + skuCode));
        System.out.println("MMMMMMMMMM"+ inventory.getStock());
        return inventory.getStock() > 0;
        //return inventory;
    }
    @GetMapping("/inventory")
    @PreAuthorize("hasAuthority('app-manager')")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> findAll() {
        System.out.println("XXXXXXXXXXXXXXXXXXXX");
        return inventoryRepository.findAll();
    }
}
