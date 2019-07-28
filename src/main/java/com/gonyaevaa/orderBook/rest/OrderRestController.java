package com.gonyaevaa.orderBook.rest;

import com.gonyaevaa.orderBook.mapper.OrderMapper;
import com.gonyaevaa.orderBook.model.Order;
import com.gonyaevaa.orderBook.model.dto.OrderDto;
import com.gonyaevaa.orderBook.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderRestController(OrderService orderService,
                               OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("")
    public List<OrderDto> listAllOrders() {
        log.info("Show all orders");

        List<Order> orders = this.orderService.findAllOrders();

        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = this.orderMapper.toOrderDto(order);
            orderDto.setTotalPayment(this.orderService.calculateOrder(order));
            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }

    @GetMapping("{orderId}")
    public OrderDto getOrder(@PathVariable("orderId") Long orderId) {
        log.info("Get order with id {}", orderId);

        Order order = this.orderService.findOrderById(orderId);
        OrderDto orderDto = this.orderMapper.toOrderDto(order);
        orderDto.setTotalPayment(this.orderService.calculateOrder(order));

        return orderDto;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@Valid @RequestBody OrderDto orderDto) {
        log.info("Create order : {}", orderDto);

        Order newOrder = this.orderMapper.toOrder(orderDto);
        this.orderService.saveOrder(newOrder);

        OrderDto newOrderDto = this.orderMapper.toOrderDto(newOrder);
        newOrderDto.setTotalPayment(this.orderService.calculateOrder(newOrder));

        return newOrderDto;
    }

    @PutMapping("/add/{orderId}")
    public OrderDto addBookToOrder(@PathVariable("orderId") Long orderId,
                                   @RequestBody Long bookId) {
        log.info("Add book with id {} to order with id {}",
                bookId, orderId);

        Order currentOrder = this.orderService.addBookToOrder(orderId, bookId);

        OrderDto newOrderDto = this.orderMapper.toOrderDto(currentOrder);
        newOrderDto.setTotalPayment(this.orderService.calculateOrder(currentOrder));

        return newOrderDto;
    }

    @DeleteMapping("/del/{orderId}")
    public OrderDto delBookFromOrder(@PathVariable("orderId") Long orderId,
                                     @RequestBody Long bookId) {
        log.info("Delete book with id {} from order with id {}",
                bookId, orderId);

        Order currentOrder = this.orderService.delBookFromOrder(orderId, bookId);

        OrderDto newOrderDto = this.orderMapper.toOrderDto(currentOrder);
        newOrderDto.setTotalPayment(this.orderService.calculateOrder(currentOrder));

        return newOrderDto;
    }

    @PutMapping("{orderId}")
    public void confirmOrder(@PathVariable("orderId") Long orderId) {
        log.info("Confirm order with id {}", orderId);

        this.orderService.confirmOrder(orderId);
    }

    @PatchMapping("{orderId}")
    public void cancelOrder(@PathVariable("orderId") Long orderId) {
        log.info("Cancel order with id {}", orderId);

        this.orderService.cancelOrder(orderId);
    }
}
