package com.gdsc.forder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class EditFillDTO {

    @ApiModelProperty()
    private String fillName;

    @ApiModelProperty(example = "12:00")
    private LocalTime fillTime;

}
