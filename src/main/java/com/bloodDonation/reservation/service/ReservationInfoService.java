package com.bloodDonation.reservation.service;

import com.bloodDonation.admin.reservation.controllers.RequestReservation;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.reservation.controllers.ReservationSearch;
import com.bloodDonation.reservation.entities.QReservation;
import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ReservationRepository reservationRepository;
    private final HttpServletRequest request;

    private Reservation get(Long bookCode) {
        Reservation reservation = reservationRepository.findById(bookCode).orElseThrow(ReservationNotFoundException::new);

        //추가작업 필요

        return reservation;
    }

    public RequestReservation getForm(Long bookCode) {
        Reservation reservation = get(bookCode);
        RequestReservation form = new ModelMapper().map(reservation, RequestReservation.class);

        String donorTel = reservation.getDonnerTel();

        form.setBookCode(reservation.getBookCode());
        form.setDonorName(reservation.getMember().getMName());
        form.setDonerTel(StringUtils.hasText(donorTel) ? donorTel.split("-") : null);
        form.setBookType(reservation.getBookType().name());
        form.setCenter(reservation.getCenter().getCName());

        form.setMode("edit_reservation");

        return form;
    }

    public ListData<Reservation> getList(ReservationSearch search) {
        QReservation reservation = QReservation.reservation;

        BooleanBuilder andBuilder = new BooleanBuilder();

        //검색 조건 키워드들
        List<Long> memberSeq = search.getMemberSeq();
        List<Long> bookCode = search.getBookCode();
        List<String> userId = search.getUserId();
        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();


        //회원번호로 조회
        if(memberSeq != null && !memberSeq.isEmpty()) {
            andBuilder.and(reservation.member.userNo.in(memberSeq));
        }
        return null;
    }
}
