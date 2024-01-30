package com.bloodDonation.reservation.controllers;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.AlertRedirectException;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.reservation.service.ReservationDateService;
import com.bloodDonation.reservation.service.ReservationInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class ReservationMainValidator implements Validator {

    private final ReservationDateService dateService;
    private final ReservationInfoService infoService;
    private final HttpServletRequest request;
    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestReservation.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestReservation form = (RequestReservation) target;

        String mode = StringUtils.hasText(form.getMode()) ? form.getMode() : "step1";
        if (mode.equals("step2")) {
            validateStep2(form, errors);
        } else if (mode.equals("admin_add")) {
            validateStep1(form,errors);
            validateStep2(form, errors);
        } else {
            validateStep1(form, errors);
        }
    }

    /**
     * step1 검증
     *
     * @param form
     * @param errors
     */
    private void validateStep1(RequestReservation form, Errors errors) {
        LocalDate date = form.getDate();
        Long cCode = form.getCCode();


        /**
         * 로그인 필요(로그인 상태가 아니면 로그인 페이지로 이동)
         */
        if (!memberUtil.isLogin()) {
            String url = request.getContextPath() + "/member/login?redirectURL=/center/" + cCode + "#reservation_box";
            throw new AlertRedirectException(Utils.getMessage("Required.login", "errors"), url, "parent", HttpStatus.UNAUTHORIZED);
        }

        //필수항목 검증
        if (date == null) {
            errors.rejectValue("date", "NonNull");
        }

        //예약 가능일자 체크
        if (!dateService.checkAvailable(cCode, date.toString())) {
            errors.rejectValue("date", "NotAvailable");
        }


    }

    /**
     * step2 검증
     *
     * @param form
     * @param errors
     */
    private void validateStep2(RequestReservation form, Errors errors) {

            LocalDate date = form.getDate();
            LocalTime time = form.getTime();

            if (time == null) {

                errors.rejectValue("time", "NonNull");
            }

            int capacity = infoService.getAvailableCapacity(form.getCCode(), LocalDateTime.of(date, time));

            int persons = form.getPersons();
            if (persons < 1 || persons > capacity ) {
                errors.rejectValue("persons", "Size");
            }

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "donorTel", "NotBlank");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bookType", "NotBlank");

        }

    }

