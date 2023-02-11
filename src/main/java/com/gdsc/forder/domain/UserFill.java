package com.gdsc.forder.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
public class UserFill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userFillId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fill_id")
    private Fill fill;

    @Column(name = "fill_check")
    private Boolean fillCheck;

}
