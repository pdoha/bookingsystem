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

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Lob
    private String data; // 설문 데이터  - JSON으로 기록

    @Column(length=3, nullable = false)
    private int positive;

    @Column(length=3, nullable = false)
    private int negative;
}
