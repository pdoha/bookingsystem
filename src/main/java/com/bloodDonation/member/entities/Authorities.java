package com.bloodDonation.member.entities;


import com.bloodDonation.member.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
@Table(indexes=@Index(name="uq_authority", columnList="userNo, authority",
        unique=true))
public class Authorities {
    @Id
    @GeneratedValue
    private Long seq; //일련번호

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority authority;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="userNo")
    private Member member;

}
