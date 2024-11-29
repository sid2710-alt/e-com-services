package com.example.springpractise.order_servie.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.springpractise.order_servie.dto.OrderLineItemsDto;
import com.example.springpractise.order_servie.dto.OrderRequest;
import com.example.springpractise.order_servie.model.Order;
import com.example.springpractise.order_servie.model.OrderLineItems;
import com.example.springpractise.order_servie.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;

  public void placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());
    List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsList().stream().map(this::mapToDto).toList();
    order.setOrderLineItemsList(orderLineItems);
    orderRepository.save(order);
  }

  private OrderLineItems mapToDto (OrderLineItemsDto orderLineItemDto) {
    OrderLineItems orderLineItems = new OrderLineItems();
    orderLineItems.setPrice(orderLineItemDto.getPrice());
    orderLineItems.setQuantity(orderLineItemDto.getQuantity());
    orderLineItems.setSkuCode(orderLineItemDto.getSkuCode());
    return orderLineItems;
  }
}