package com.gonyaevaa.orderBook.model.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gonyaevaa.orderBook.model.dto.UserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel(value = "UserResponseWithIdAndOrders")
public class UserResponseWithIdAndOrders extends UserDto {

    @ApiModelProperty(example = "1")
    private Long userId;

    @ApiModelProperty(example = "[1, 2]", position = 3)
    @JsonProperty("orders")
    private List<Long> orderDtoList;
}