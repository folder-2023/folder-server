package com.gdsc.forder.global.security.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST"),
    USER("ROLE_USER");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String value(){
        return role;
    }
}
