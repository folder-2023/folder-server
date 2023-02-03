package com.gdsc.forder.global.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "family_id")
    private Integer familyId;

    @Column(name = "login_id", length = 50, unique = true)
    private String login_id;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(name = "user_role", length = 50)
    private String userRole;

    @Column(name = "user_code", length = 50, unique = true)
    private String userCode;

    @Column(name = "phone_Number", length = 50)
    private String phoneNumber;

    @Column(name = "wake_time")
    private LocalDateTime wakeTime;

    @Column(name = "sleep_time")
    private LocalDateTime sleepTime;

}
