package com.bloodDonation.board.service;


import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class BoardDataNotFoundException extends AlertBackException {
    public BoardDataNotFoundException() {
        super(Utils.getMessage("NotFound.boardData", "errors"), HttpStatus.NOT_FOUND);
    }
}
