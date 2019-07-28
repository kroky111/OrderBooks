package com.gonyaevaa.orderBook.mapper;

import com.gonyaevaa.orderBook.config.MapConfig;
import com.gonyaevaa.orderBook.model.Order;
import com.gonyaevaa.orderBook.model.User;
import com.gonyaevaa.orderBook.model.dto.UserDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapConfig.class)
public interface UserMapper {

    @Mappings( {
            @Mapping(target = "orderDtoList", source = "orders")
    })
    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);

    @InheritInverseConfiguration
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "orders", ignore = true)
    User toUser(UserDto userDto);

    @InheritConfiguration
    void userDtoIntoUser(UserDto userDto, @MappingTarget User user);

    default Long fromOrder(Order order) {
        return order.getOrderId();
    }
}