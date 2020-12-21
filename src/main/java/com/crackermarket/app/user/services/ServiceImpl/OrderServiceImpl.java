package com.crackermarket.app.user.services.ServiceImpl;

import com.crackermarket.app.user.entities.Order;
import com.crackermarket.app.user.repository.OrderDAO;
import com.crackermarket.app.user.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;

    @Autowired
    OrderServiceImpl(OrderDAO orderDAO) {this.orderDAO = orderDAO;}
    OrderServiceImpl(){}

    @Override
    public Order findOrderById(UUID id) {
        return orderDAO.findOrderById(id);
    }

    @Override
    public List<Order> findUserOrders(UUID user_id, int page, int resultsInPage) {
        return orderDAO.findUserOrders(user_id, page, resultsInPage);
    }

    @Override
    public List<Order> findAllOrders(int page, int resultsInPage) {
        return orderDAO.findAllOrders(page, resultsInPage);
    }

    @Override
    public void saveOrder(Order order) {
        orderDAO.saveOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderDAO.deleteOrder(order);
    }
}
