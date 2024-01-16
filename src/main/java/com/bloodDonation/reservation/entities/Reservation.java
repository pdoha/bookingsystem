package com.bloodDonation.reservation.entities;

import com.bloodDonation.center.entities.CenterInfo;
import com.bloodDonation.commons.entities.Base;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.reservation.constants.DonationType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Reservation extends Base {

    @Id @GeneratedValue
    private  Long bookCode; //예약 코드

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_userNo")
    private Member member; //회원쪽정보 가져올 것임

    @Column(length=15, nullable = false)
    private String donnerTel; //헌혈자 전화번호

     @Enumerated(EnumType.STRING)
     @Column(length=25, nullable = false)
     private DonationType bookType;  //헌혈 종류

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cCode")
    private CenterInfo center; //예약할 센터

    private LocalDateTime bookDateTime; //예약시간


}
