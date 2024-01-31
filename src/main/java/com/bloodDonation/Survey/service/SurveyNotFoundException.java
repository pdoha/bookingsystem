package com.bloodDonation.Survey.service;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class SurveyNotFoundException extends AlertBackException {
    public SurveyNotFoundException() {
        super(Utils.getMessage("NotFound.survey", "errors"), HttpStatus.NOT_FOUND);
    }
}
