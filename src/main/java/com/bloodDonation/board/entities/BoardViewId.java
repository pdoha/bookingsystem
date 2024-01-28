package com.bloodDonation.board.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 24.01.18
 * 기본키를 조합하기 위해 만든 클래스
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardViewId {
    private Long seq;
    private int uid;

}
