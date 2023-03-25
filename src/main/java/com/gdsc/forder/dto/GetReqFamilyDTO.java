package com.gdsc.forder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetReqFamilyDTO {

//친구요청한 유저의 이름
    @ApiModelProperty(value = "요청한 유저의 이름")
    private String username;
    //친구요청한 유저의 코드

    @ApiModelProperty(value = "요청한 유저의 코드")
    private long userCode;

}
