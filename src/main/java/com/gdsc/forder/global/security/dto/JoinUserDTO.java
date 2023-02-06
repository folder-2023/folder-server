package com.gdsc.forder.global.security.dto;

import com.gdsc.forder.global.security.domain.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class JoinUserDTO {
    @ApiModelProperty
    private String email;
    @ApiModelProperty
    private String password;
    @ApiModelProperty
    private Role role;
}
