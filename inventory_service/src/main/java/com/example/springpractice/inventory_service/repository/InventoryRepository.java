package com.example.springpractice.inventory_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springpractice.inventory_service.model.Inventory;

public interface  InventoryRepository extends JpaRepository<Inventory, Long>{

    public Optional<Inventory> findBySkuCode(String skuCode);
}
