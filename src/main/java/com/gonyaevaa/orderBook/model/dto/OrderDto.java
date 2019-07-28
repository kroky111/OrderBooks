package com.gonyaevaa.orderBook.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ToString
@ApiModel(value = "Order")
public class OrderDto {

    @ApiModelProperty(hidden = true)
    @JsonProperty("orderId")
    private Long orderId;

    @NotNull
    @ApiModelProperty(example = "1", required = true, position = 1)
    @JsonProperty("userId")
    private Long userId;

    @ApiModelProperty(hidden = true)
    @JsonProperty("totalPayment")
    private BigDecimal totalPayment;

    @NotNull
    @ApiModelProperty(example = "[1, 2]", required = true, position = 3)
    @JsonProperty("books")
    private List<Long> bookDtoList;

    @ApiModelProperty(example = "PENDING", position = 4)
    @JsonProperty("status")
    private Status status;

    public OrderDto() {
        status = Status.PENDING;
    }

    public enum Status {
        PENDING, PAID, CANCELLED
    }
}
