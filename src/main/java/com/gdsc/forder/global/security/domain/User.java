package com.gdsc.forder.global.security.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 200, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String email, String encodedPassword, Role role) {
        this.email = email;
        this.password = encodedPassword;
        this.role = role;
    }

    private Collection<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


//    @Id
//    @Column(name = "user_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    @Column(name = "family_id")
//    private Integer familyId;
//
//    @Column(name = "login_id", length = 50, unique = true)
//    private String login_id;
//
//    @Column(name = "password", length = 50)
//    private String password;
//
//    @Column(name = "user_name", length = 50)
//    private String userName;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;
//
//    @Column(name = "user_code", length = 50, unique = true)
//    private String userCode;
//
//    @Column(name = "phone", length = 50)
//    private String phone;
//
//    @Column(name = "wake_time")
//    private LocalDateTime wakeTime;
//
//    @Column(name = "sleep_time")
//    private LocalDateTime sleepTime;
//
//    public Member orElseThrow(Object o) {
//    }
}
