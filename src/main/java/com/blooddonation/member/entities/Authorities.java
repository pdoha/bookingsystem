package com.blooddonation.member.entities;

import com.blooddonation.member.Authority;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(indexes=@Index(name="uq_authority", columnList="userNo, authority",
                        unique=true))
public class Authorities {
    @Id
    @GeneratedValue
    private Long seq;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority authority;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="userNo")
    private Member member;

}
