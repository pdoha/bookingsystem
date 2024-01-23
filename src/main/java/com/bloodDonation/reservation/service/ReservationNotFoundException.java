package com.bloodDonation.reservation.service;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends AlertBackException {
    public ReservationNotFoundException() {
        super(Utils.getMessage("NotFound.reservation", "errors"), HttpStatus.NOT_FOUND);
    }
}
