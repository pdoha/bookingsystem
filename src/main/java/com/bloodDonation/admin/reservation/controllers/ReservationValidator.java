package com.bloodDonation.admin.reservation.controllers;

import com.bloodDonation.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ReservationValidator implements Validator {
    private final ReservationRepository reservationRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestReservation.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestReservation form = (RequestReservation) target;
        Long bookCode = form.getBookCode();
        String mode = form.getMode();
        if(mode.equals("add") && StringUtils.hasText(String.valueOf(bookCode)) && reservationRepository.existsById(bookCode)) {
            errors.rejectValue("bookCode", "");
        }
    }
}
