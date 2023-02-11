package com.gdsc.forder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class AddFillDTO {

    @ApiModelProperty()
    private List<String> fills = new ArrayList<>();

    @ApiModelProperty()
    private List<String> fillTimes = new ArrayList<>();

}
