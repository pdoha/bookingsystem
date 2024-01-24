package com.bloodDonation.reservation.service;

import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationDeleteService {
    private final ReservationRepository repository;
    private final ReservationInfoService infoService;

    public void delete(Long bookCode) {
        Reservation reservation = infoService.get(bookCode);

        repository.delete(reservation);
        repository.flush();
    }
}
