package com.gonyaevaa.orderBook.model.resp;

import com.gonyaevaa.orderBook.model.dto.BookDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@ApiModel(value = "BookResponse")
public class BookResponse extends BookDto {

    @ApiModelProperty(example = "1")
    private Long bookId;
}
