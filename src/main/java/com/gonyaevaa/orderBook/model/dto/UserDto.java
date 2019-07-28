package com.gonyaevaa.orderBook.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.gonyaevaa.orderBook.model.Views;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = {"firstName"})
@NoArgsConstructor
public class UserDto {

    @JsonView(Views.PartInfo.class)
    private Long userId;

    @NotNull
    @JsonView(Views.PartInfo.class)
    private String firstName;

    @NotNull
    @JsonView(Views.PartInfo.class)
    private String lastName;

    @JsonProperty("orders")
    private List<Long> orderDtoList;
}
