package com.gdsc.forder.global.security.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_OLD("ROLE_OLD", "노인"),
    ROLE_PRO("ROLE_PRO", "보호자");

    private final String key;
    private final String title;
}
