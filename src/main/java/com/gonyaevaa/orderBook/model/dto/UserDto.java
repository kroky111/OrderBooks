package com.gonyaevaa.orderBook.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.gonyaevaa.orderBook.model.Views;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = {"firstName"})
@NoArgsConstructor
@ApiModel(value = "User")
public class UserDto {

    @JsonView(Views.PartInfo.class)
    @ApiModelProperty(hidden = true)
    private Long userId;

    @NotNull
    @ApiModelProperty(example = "John", required = true, position = 1)
    @JsonView(Views.PartInfo.class)
    private String firstName;

    @NotNull
    @ApiModelProperty(example = "Smith", required = true, position = 2)
    @JsonView(Views.PartInfo.class)
    private String lastName;

    @ApiModelProperty(hidden = true)
    @JsonProperty("orders")
    private List<Long> orderDtoList;
}
