package com.gonyaevaa.orderBook.mapper;

import com.gonyaevaa.orderBook.config.MapConfig;
import com.gonyaevaa.orderBook.model.Book;
import com.gonyaevaa.orderBook.model.Order;
import com.gonyaevaa.orderBook.model.User;
import com.gonyaevaa.orderBook.model.dto.OrderDto;
import com.gonyaevaa.orderBook.service.BookService;
import com.gonyaevaa.orderBook.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.gonyaevaa.orderBook.model.Order.Status;

@Mapper(config = MapConfig.class)
public abstract class OrderMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @Mappings({
            @Mapping(target = "userId", source = "user"),
            @Mapping(target = "bookDtoList", source = "books"),
            @Mapping(target = "totalPayment", ignore = true)
    })
    public abstract OrderDto toOrderDto(Order order);

    Long fromUser(User user) {
        return user.getUserId();
    }

    Long fromBook(Book book) {
        return book.getBookId();
    }

    public Order toOrder(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }

        Order order = new Order();

        order.setBooks(longListToBookList(orderDto.getBookDtoList()));
        order.setUser(userService.findUserById(orderDto.getUserId()));
        order.setStatus(statusDtoToStatus(orderDto.getStatus()));

        return order;
    }

    private List<Book> longListToBookList(List<Long> list) {
        if (list == null) {
            return null;
        }

        List<Book> list1 = new ArrayList<>();
        for (Long long1 : list) {
            list1.add(bookService.findBookById(long1));
        }

        return list1;
    }

    private Status statusDtoToStatus(OrderDto.Status status) {
        if (status == null) {
            return null;
        }

        Status status1;

        switch (status) {
            case PENDING:
                status1 = Status.PENDING;
                break;
            case PAID:
                status1 = Status.PAID;
                break;
            case CANCELLED:
                status1 = Status.CANCELLED;
                break;
            default:
                throw new IllegalArgumentException("Unexpected enum constant: " + status);
        }

        return status1;
    }
}
