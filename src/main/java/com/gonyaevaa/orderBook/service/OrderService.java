package com.gonyaevaa.orderBook.service;

import com.gonyaevaa.orderBook.model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    List<Order> findAllOrders();

    void saveOrder(Order order);

    Order findOrderById(Long id);

    Order cancelOrder(Long orderId);

    Order confirmOrder(Long orderId);

    Order delBookFromOrder(Long orderId, Long bookId);

    Order addBookToOrder(Long orderId, Long bookId);

    BigDecimal calculateOrder(Order order);
}
