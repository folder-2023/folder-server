package com.gdsc.forder.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInDTO {

    private JoinUserDTO joinUserDTO;
    private AddFillDTO addFillDTO;
}
