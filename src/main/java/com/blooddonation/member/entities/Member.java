package com.blooddonation.member.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Member {
    @Id @GeneratedValue
    private Long userNo;

    @Column(length=40, nullable = false, unique = true)
    private String userId;

    @Column(length=20, nullable = false)
    private String userPw;

    @Column(length=40, nullable = false, unique = true)
    private String email;

    @Column(length=30, nullable = false)
    private String mName;

    @Column(length=40, nullable = false)
    private String phone;

    @Column(length=20, nullable = false)
    private String birth;

    @Column(length=10, nullable = false)
    private String bldType;

}
