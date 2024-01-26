package com.bloodDonation.chatting.entities;

import com.bloodDonation.commons.entities.Base;
import com.bloodDonation.member.entities.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistory extends Base {

    @Id @GeneratedValue
    private Long seq;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "memberSeq")
    private Member member;

    @Column(length = 40, nullable = false)
    private String nickName;

    @Column(length = 50, nullable = false)
    private String message;
}
