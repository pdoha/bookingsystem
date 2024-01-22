package com.bloodDonation.mypage.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Survey {
    //private Member member;
    @Column(length=3, nullable = false)
    private int positive;

    @Column(length=3, nullable = false)
    private int negative;
}
