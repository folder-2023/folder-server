package com.gdsc.forder.global.security.dto;

import com.gdsc.forder.global.security.domain.Role;
import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel
public class UserDTO {
    private Long id;
    private String loginId;
    private String username;
    private Role roles;
}
