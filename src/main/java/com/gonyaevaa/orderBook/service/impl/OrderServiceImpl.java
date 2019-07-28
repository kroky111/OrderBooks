package com.gonyaevaa.orderBook.service.impl;

import com.gonyaevaa.orderBook.exception.NotFoundException;
import com.gonyaevaa.orderBook.model.Book;
import com.gonyaevaa.orderBook.model.Order;
import com.gonyaevaa.orderBook.repository.OrderRepository;
import com.gonyaevaa.orderBook.service.BookService;
import com.gonyaevaa.orderBook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final BookService bookService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, BookService bookService) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order with id " + id
                        + " not found"));
    }

    @Override
    public Order addBookToOrder(Long orderId, Long bookId) {
        Order currentOrder = findOrderById(orderId);
        if (currentOrder.isPending()) {
            Book currentBook = this.bookService.findBookById(bookId);
            currentOrder.getBooks().add(currentBook);
            saveOrder(currentOrder);
        } else {
            throw new IllegalStateException("Order status must be pending");
        }
        return currentOrder;
    }

    @Override
    public Order delBookFromOrder(Long orderId, Long bookId) {
        Order currentOrder = findOrderById(orderId);
        if (currentOrder.isPending()) {
            Book currentBook = this.bookService.findBookById(bookId);
            currentOrder.getBooks().remove(currentBook);
            saveOrder(currentOrder);
        } else {
            throw new IllegalStateException("Order status must be pending");
        }
        return currentOrder;
    }

    @Override
    public Order confirmOrder(Long orderId) {
        Order currentOrder = findOrderById(orderId);
        if (currentOrder.isPending()) {
            currentOrder.setStatus(Order.Status.PAID);
            saveOrder(currentOrder);
        } else {
            throw new IllegalStateException("Order status must be pending");
        }
        return currentOrder;
    }

    @Override
    public Order cancelOrder(Long orderId) {
        Order currentOrder = findOrderById(orderId);
        if (currentOrder.isCancelled()) {
            throw new IllegalStateException("Order status already cancelled");
        } else {
            currentOrder.setStatus(Order.Status.CANCELLED);
            saveOrder(currentOrder);
        }
        return currentOrder;
    }

    @Override
    public BigDecimal calculateOrder(Order order) {
        BigDecimal totalSum = new BigDecimal(0);
        for (Book book : order.getBooks()) {
            totalSum = totalSum.add(book.getPrice());
        }
        return totalSum;
    }
}
