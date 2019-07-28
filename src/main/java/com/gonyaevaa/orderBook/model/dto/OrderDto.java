package com.gonyaevaa.orderBook.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ToString
public class OrderDto {

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("userId")
    private Long userId;

    @NotNull
    @JsonProperty("totalPayment")
    private BigDecimal totalPayment;

    @NotNull
    @JsonProperty("books")
    private List<Long> bookDtoList;

    @JsonProperty("status")
    private Status status;

    public OrderDto() {
        status = Status.PENDING;
    }

    public enum Status {
        PENDING, PAID, CANCELLED
    }
}
