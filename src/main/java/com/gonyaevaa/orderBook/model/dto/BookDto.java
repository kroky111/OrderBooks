package com.gonyaevaa.orderBook.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ToString(exclude = {"orderDtoList"})
@EqualsAndHashCode(of = {"title"})
@NoArgsConstructor
@ApiModel(value = "Book")
public class BookDto {

    @ApiModelProperty(hidden = true)
    private Long bookId;

    @NotNull
    @ApiModelProperty(example = "Thinking in Java", required = true, position = 1)
    private String title;

    @NotNull
    @ApiModelProperty(example = "1000", required = true, position = 2)
    private BigDecimal price;

    @JsonIgnore
    private List<Long> orderDtoList;
}
