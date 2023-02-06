package com.gdsc.forder.global.security.dto;

import com.gdsc.forder.global.security.domain.Role;
import com.gdsc.forder.global.security.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class JoinUserDTO {

    @ApiModelProperty
    @NotBlank(message = "아이디를 입력해주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
//            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    private String checkedPassword;

    private Role role;

    @Builder
    public User toEntity(){
        return User.builder()
                .email(email)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
