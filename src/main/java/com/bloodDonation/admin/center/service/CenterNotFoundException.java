package com.bloodDonation.admin.center.service;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class CenterNotFoundException extends AlertBackException {
    public CenterNotFoundException(){
        super(Utils.getMessage("NotFound.center","errors"), HttpStatus.NOT_FOUND);
    }
}
