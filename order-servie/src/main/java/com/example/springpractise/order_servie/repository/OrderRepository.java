package com.example.springpractise.order_servie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springpractise.order_servie.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
