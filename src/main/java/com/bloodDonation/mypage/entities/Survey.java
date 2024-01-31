package com.bloodDonation.mypage.entities;

import com.bloodDonation.member.entities.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Survey {

    @Id
    @GeneratedValue
    private Long seq;

    private boolean q1;
    private String q1Desc;
    private boolean q2;
    private String q2Desc;
    private boolean q3;
    private String q3Desc;
    private boolean q4;
    private String q4Desc;
    private boolean q5;
    private String q5Desc;
    private boolean q6;
    private String q6Desc;
    private boolean q7;
    private String q7Desc;
    private boolean q8;
    private String q8Desc;
    private boolean q9;
    private String q9Desc;
    private String q10;
    private String q10Desc;
    private String q11;
    private String q11Desc;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="userNo")
    private Member member;

    @Transient
    private int positive;

    @Transient
    private int negative;

    @Transient
    private boolean result;
}