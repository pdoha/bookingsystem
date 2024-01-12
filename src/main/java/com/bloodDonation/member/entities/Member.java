package com.bloodDonation.member.entities;

import com.bloodDonation.commons.entities.Base;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
public class Member extends Base {
    @Id @GeneratedValue
    private Long userNo;

    @Column(length=40, nullable = false, unique = true)
    private String userId;

    @Column(length=65, nullable = false)
    private String userPw;

    @Column(length=40, nullable = false, unique = true)
    private String email;

    @Column(length=30, nullable = false)
    private String mName; //이름


    @Column(length = 30, nullable = false)
    private String ecode;  //이메일인증코드

    /*@Column(length=40, nullable = false)
    private String phone;
    회원가입할때 받지말고 나중에 회원정보수정에 입력할수있게 수정


    */
    @ToString.Exclude
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Authorities> authorities = new ArrayList<>();
}
