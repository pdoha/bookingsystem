package com.bloodDonation.member.entities;

import com.bloodDonation.commons.entities.Base;
import com.bloodDonation.mypage.entities.Survey;
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

    @Column(length = 65, nullable = false)
    private String gid; //그룹아이디

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


   /* @Column(length=40)
    private String mobile;

    */
    //개인정보변경에서 변경정보를 멤버에 저장해야됨
    @Column(length=3)
    private String bldType; // 혈액형

    @Column(length=3)
    private String bldType2; // RH+ -


    //권한
    @ToString.Exclude
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Authorities> authorities = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Survey> surveys = new ArrayList<>();

    //회원탈퇴시-memberinfo쪽에 isEnable()의 리턴 시 필요
    private boolean enable = true;
}
