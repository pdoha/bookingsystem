package com.bloodDonation.board.entities;


import com.bloodDonation.member.entities.Member;

public interface AuthCheck {
    boolean isEditable();
    boolean isDeletable();
    boolean isMine();

    Member getMember();
}