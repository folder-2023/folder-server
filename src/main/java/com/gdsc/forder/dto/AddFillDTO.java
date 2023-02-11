package com.gdsc.forder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class AddFillDTO {

    @ApiModelProperty()
    private List<String> fills = new ArrayList<>();

    @ApiModelProperty(example = "12:00")
    private List<String> fillTimes = new ArrayList<>();

}
