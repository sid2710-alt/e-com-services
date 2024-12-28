package com.example.springpractice.inventory_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springpractice.inventory_service.model.Inventory;

public interface  InventoryRepository extends JpaRepository<Inventory, Long>{
    public List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
