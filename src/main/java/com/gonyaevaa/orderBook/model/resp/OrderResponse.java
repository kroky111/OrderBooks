package com.gonyaevaa.orderBook.model.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gonyaevaa.orderBook.model.dto.OrderDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@ApiModel(value = "OrderResponse")
public class OrderResponse extends OrderDto {

    @ApiModelProperty(example = "1")
    @JsonProperty("orderId")
    private Long orderId;

    @ApiModelProperty(example = "1000", position = 2)
    @JsonProperty("totalPayment")
    private BigDecimal totalPayment;
}
