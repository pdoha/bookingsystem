package com.bloodDonation.board.entities;


import com.bloodDonation.member.entities.Member;

/**
 * 24.01.24
 * 작성한 글 수정,삭제할 때
 */
public interface AuthCheck {
    boolean isEditable(); //수정 가능 여부
    boolean isDeletable(); //삭제 가능 여부
    boolean isMine(); //사용자 본인의 것인지 여부

    Member getMember();
}