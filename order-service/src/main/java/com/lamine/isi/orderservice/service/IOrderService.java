package com.lamine.isi.orderservice.service;

import com.lamine.isi.orderservice.model.Order;
import com.lamine.isi.orderservice.model.OrderItem;

import java.util.List;
import java.util.Map;

public interface IOrderService {
    List<Order> getAll();

    Order getById(Long id);

    List<Order> getByUserId(Long userId);

    Order save(Order order);

    Order update(Long id, Order order);

    Order partialUpdate(Long id, Map<String, Object> updates);

    void delete(Long id);

    List<OrderItem> getOrderItems(Long orderId);

    OrderItem addOrderItem(Long orderId, OrderItem orderItem);

    void removeOrderItem(Long orderId, Long orderItemId);
}