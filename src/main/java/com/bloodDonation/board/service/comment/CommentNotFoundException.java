package com.bloodDonation.board.service.comment;


import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.AlertException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends AlertException {

    public CommentNotFoundException () {
        super(Utils.getMessage("NotFound.comment", "errors"), HttpStatus.NOT_FOUND);

    }

}
