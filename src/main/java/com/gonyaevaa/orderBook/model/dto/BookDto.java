package com.gonyaevaa.orderBook.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"orderDtoList"})
@EqualsAndHashCode(of = {"title"})
@NoArgsConstructor
public class BookDto {

    private Long bookId;

    private String title;

    private BigDecimal price;

    @JsonIgnore
    private List<Long> orderDtoList;
}
