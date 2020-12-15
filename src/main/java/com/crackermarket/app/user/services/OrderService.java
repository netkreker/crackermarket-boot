package com.crackermarket.app.user.services;

import com.crackermarket.app.user.entities.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    // Search
    Order findOrderById(UUID id);
    List<Order> findUserOrders(UUID user_id);
    List<Order> findAllOrders();

    // Updating table
    void saveOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(Order order);
}
