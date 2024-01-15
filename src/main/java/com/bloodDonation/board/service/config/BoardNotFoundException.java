package com.bloodDonation.board.service.config;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends AlertBackException {
    public BoardNotFoundException() {
        //문구는 고정해서 넣기
        super(Utils.getMessage("NotFound.board", "errors"), HttpStatus.NOT_FOUND);
    }
}