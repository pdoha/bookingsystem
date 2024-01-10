package com.bloodDonation.member.entities;

import com.bloodDonation.commons.entities.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
public class Member extends Base {
    @Id @GeneratedValue
    private Long userNo;

    @Column(length=40, nullable = false, unique = true)
    private String userId;

    @Column(length=20, nullable = false)
    private String userPw;

    @Column(length=40, nullable = false, unique = true)
    private String email;

    @Column(length=30, nullable = false)
    private String mName; //이름

    /*@Column(length=40, nullable = false)
    private String phone;
    회원가입할때 받지말고 나중에 회원정보수정에 입력할수있게 수정

    */



}
