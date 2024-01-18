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

    //비밀번호 확인은 디비에 들어가는게 아니고 form에서만 확인하는것!

    @Column(length=40, nullable = false, unique = true)
    private String email;

    @Column(length=30, nullable = false)
    private String mName; //이름

    //주소
    @Column(length=10)
    private String zonecode;//우편번호

    @Column(length=100)
    private String address;//주소

    @Column(length=100)
    private String addressSub;//상세주소


    /*@Column(length=40, nullable = false)
    private String phone;
    회원가입할때 받지말고 나중에 회원정보수정에 입력할수있게 수정


    */
    @ToString.Exclude
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Authorities> authorities = new ArrayList<>();
}
