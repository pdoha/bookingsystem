package com.bloodDonation.board.entities;

import com.bloodDonation.commons.entities.BaseMember;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BoardData extends BaseMember {
    @Id @GeneratedValue
    private Long seq;

    private String subject;
    private String content;

}
