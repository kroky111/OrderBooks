package com.gonyaevaa.orderBook.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"orderDtoList"})
@EqualsAndHashCode(of = {"title"})
@NoArgsConstructor
public class BookDto {

    private Long bookId;

    @NotNull
    private String title;

    @NotNull
    private BigDecimal price;

    @JsonIgnore
    private List<Long> orderDtoList;
}
