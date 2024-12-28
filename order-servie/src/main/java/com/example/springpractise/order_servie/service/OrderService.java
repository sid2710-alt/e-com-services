package com.example.springpractise.order_servie.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.springpractise.order_servie.dto.InventoryResponse;
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
  private final WebClient.Builder webClient;

  public void placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());
    List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsList().stream().map(this::mapToDto).toList();
    order.setOrderLineItemsList(orderLineItems);
    List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

    InventoryResponse[] inventoryResponseArray = webClient.build().get().uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                   .retrieve()
                   .bodyToMono(InventoryResponse[].class)
                   .block();
    boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                    .allMatch(InventoryResponse::isInStock);               
    if (allProductsInStock) {
      orderRepository.save(order);
    } else {
      throw new IllegalArgumentException("Product is not in stock, please try again later");
    }               
  }

  private OrderLineItems mapToDto (OrderLineItemsDto orderLineItemDto) {
    OrderLineItems orderLineItems = new OrderLineItems();
    orderLineItems.setPrice(orderLineItemDto.getPrice());
    orderLineItems.setQuantity(orderLineItemDto.getQuantity());
    orderLineItems.setSkuCode(orderLineItemDto.getSkuCode());
    return orderLineItems;
  }
}
