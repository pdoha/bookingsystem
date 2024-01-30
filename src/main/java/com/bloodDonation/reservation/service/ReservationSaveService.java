package com.bloodDonation.reservation.service;

import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.service.CenterInfoService;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.AlertException;
import com.bloodDonation.reservation.constants.DonationType;
import com.bloodDonation.reservation.constants.ReservationStatus;
import com.bloodDonation.reservation.controllers.RequestReservation;
import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationSaveService {
    private final ReservationRepository reservationRepository;
    private final ReservationInfoService infoService;
    private final CenterInfoService centerInfoService;
    private final Utils utils;

    public void saveList(List<Integer> chks) {
        if(chks ==null || chks.isEmpty()) {
            throw new AlertException("수정할 예약을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for(int chk : chks) {
            Long bookCode = Long.valueOf(utils.getParam("bookCode_" + chk));
            Reservation reservation = reservationRepository.findById(bookCode).orElse(null);
            if(reservation==null) {
                continue;
            }

            ReservationStatus status = ReservationStatus.valueOf(utils.getParam("status_" + chk));
            reservation.setStatus(status);
        }
        reservationRepository.flush();
    }

    public void save(RequestReservation form) {

        Long bookCode = form.getBookCode();
        Reservation data = infoService.get(bookCode);
        CenterInfo center = centerInfoService.get(form.getCCode());

        data.setCenter(center);
        data.setStatus(ReservationStatus.valueOf(form.getStatus()));
        data.setBookType(DonationType.valueOf(form.getBookType()));
        data.setCapacity(form.getPersons());
        data.setDonorTel(form.getDonorTel());
        data.setBookDateTime(LocalDateTime.of(form.getDate(), form.getTime()));

        reservationRepository.saveAndFlush(data);


    }
}
