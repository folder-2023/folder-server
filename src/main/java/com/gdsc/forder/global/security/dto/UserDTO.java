package com.gdsc.forder.global.security.dto;

import com.gdsc.forder.global.security.domain.Role;
import com.gdsc.forder.global.security.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private Role roles;
}
