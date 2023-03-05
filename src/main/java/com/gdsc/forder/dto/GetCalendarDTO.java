package com.gdsc.forder.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class GetCalendarDTO {

    @ApiModelProperty
    private Long calendarId;

    @ApiModelProperty
    private Long userId;

    @ApiModelProperty()
    private String content;

    @ApiModelProperty()
    private String calendarTime;

    @ApiModelProperty()
    private LocalDate calendarDate;

}
