package com.bloodDonation.file.service;


import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CommonException {
    public FileNotFoundException() {
        super(Utils.getMessage("NotFound.file","errors"), HttpStatus.NOT_FOUND);
    }
}
