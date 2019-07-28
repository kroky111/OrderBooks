package com.gonyaevaa.orderBook.rest;

import com.gonyaevaa.orderBook.mapper.OrderMapper;
import com.gonyaevaa.orderBook.model.Order;
import com.gonyaevaa.orderBook.model.dto.OrderDto;
import com.gonyaevaa.orderBook.model.resp.OrderResponse;
import com.gonyaevaa.orderBook.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "order", value = "Operations pertaining to orders")
public class OrderRestController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderRestController(OrderService orderService,
                               OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @ApiOperation(value = "View a list of available orders", response = OrderResponse.class)
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

    @ApiOperation(value = "Find book purchase order by ID", response = OrderResponse.class)
    @GetMapping("{orderId}")
    public OrderDto getOrder(@ApiParam(value = "Id of the order to be obtained", required = true)
                             @PathVariable("orderId") Long orderId) {
        log.info("Get order with id {}", orderId);

        Order order = this.orderService.findOrderById(orderId);
        OrderDto orderDto = this.orderMapper.toOrderDto(order);
        orderDto.setTotalPayment(this.orderService.calculateOrder(order));

        return orderDto;
    }

    @ApiOperation(value = "Place an order for books", response = OrderResponse.class)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@ApiParam(value = "Order object that needs to be added", name = "Order", required = true)
                                @Valid @RequestBody OrderDto orderDto) {
        log.info("Create order : {}", orderDto);

        Order newOrder = this.orderMapper.toOrder(orderDto);
        this.orderService.saveOrder(newOrder);

        OrderDto newOrderDto = this.orderMapper.toOrderDto(newOrder);
        newOrderDto.setTotalPayment(this.orderService.calculateOrder(newOrder));

        return newOrderDto;
    }

    @ApiOperation(value = "Add books to an existing order", response = OrderResponse.class)
    @PutMapping("/add/{orderId}")
    public OrderDto addBookToOrder(@ApiParam(value = "ID of the order in which the book will be added", required = true)
                                   @PathVariable("orderId") Long orderId,
                                   @ApiParam(value = "ID of the book to be added to the order", required = true)
                                   @RequestBody Long bookId) {
        log.info("Add book with id {} to order with id {}",
                bookId, orderId);

        Order currentOrder = this.orderService.addBookToOrder(orderId, bookId);

        OrderDto newOrderDto = this.orderMapper.toOrderDto(currentOrder);
        newOrderDto.setTotalPayment(this.orderService.calculateOrder(currentOrder));

        return newOrderDto;
    }

    @ApiOperation(value = "Delete book from an existing order", response = OrderResponse.class)
    @DeleteMapping("/del/{orderId}")
    public OrderDto delBookFromOrder(@ApiParam(value = "ID of the order from which the book will be deleted",
            required = true)
                                     @PathVariable("orderId") Long orderId,
                                     @ApiParam(value = "ID of the book to be removed from the order", required = true)
                                     @RequestBody Long bookId) {
        log.info("Delete book with id {} from order with id {}",
                bookId, orderId);

        Order currentOrder = this.orderService.delBookFromOrder(orderId, bookId);

        OrderDto newOrderDto = this.orderMapper.toOrderDto(currentOrder);
        newOrderDto.setTotalPayment(this.orderService.calculateOrder(currentOrder));

        return newOrderDto;
    }

    @ApiOperation(value = "Confirm the order")
    @PutMapping("{orderId}")
    public void confirmOrder(@ApiParam(value = "ID of the order to be confirmed", required = true)
                             @PathVariable("orderId") Long orderId) {
        log.info("Confirm order with id {}", orderId);

        this.orderService.confirmOrder(orderId);
    }

    @ApiOperation(value = "Cancel the order")
    @PatchMapping("{orderId}")
    public void cancelOrder(@ApiParam(value = "ID of the order to be cancelled", required = true)
                            @PathVariable("orderId") Long orderId) {
        log.info("Cancel order with id {}", orderId);

        this.orderService.cancelOrder(orderId);
    }
}
