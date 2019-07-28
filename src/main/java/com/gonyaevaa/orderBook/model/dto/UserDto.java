package com.gonyaevaa.orderBook.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = {"firstName"})
@NoArgsConstructor
public class UserDto {

    private Long userId;

    private String firstName;

    private String lastName;

    @JsonProperty("orders")
    private List<Long> orderDtoList;
}
