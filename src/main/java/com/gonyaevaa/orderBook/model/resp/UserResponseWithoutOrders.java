package com.gonyaevaa.orderBook.model.resp;

import com.gonyaevaa.orderBook.model.dto.UserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(value = "UserResponseWithoutOrders")
public class UserResponseWithoutOrders extends UserDto {

    @ApiModelProperty(example = "1")
    private Long userId;
}