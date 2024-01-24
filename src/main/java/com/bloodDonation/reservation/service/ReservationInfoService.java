package com.bloodDonation.reservation.service;

import com.bloodDonation.admin.reservation.controllers.RequestReservation;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.commons.Pagination;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.reservation.controllers.ReservationSearch;
import com.bloodDonation.reservation.entities.QReservation;
import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ReservationRepository reservationRepository;
    private final HttpServletRequest request;

    public Reservation get(Long bookCode) {
        Reservation reservation = reservationRepository.findById(bookCode).orElseThrow(ReservationNotFoundException::new);

        //추가작업 필요

        return reservation;
    }

    public RequestReservation getForm(Long bookCode) {
        Reservation reservation = get(bookCode);
        RequestReservation form = new ModelMapper().map(reservation, RequestReservation.class);

        String donorTel = reservation.getDonorTel();

        form.setBookCode(reservation.getBookCode());
        form.setDonorName(reservation.getMember().getMName());
        form.setDonorTel(StringUtils.hasText(donorTel) ? donorTel.split("-") : null);
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

        if(bookCode != null && !bookCode.isEmpty()) {
            andBuilder.and(reservation.bookCode.in(bookCode));
        }

        if(userId != null && !userId.isEmpty()) {
            andBuilder.and(reservation.member.userId.in(userId));
        }

        if(sDate != null) {
            andBuilder.and(reservation.bookDateTime.goe(LocalDateTime.of(
                    sDate, LocalTime.of(0,0,0))));
        }

        if(eDate != null) {
            andBuilder.and(reservation.bookDateTime.loe(LocalDateTime.of(eDate, LocalTime.of(23,59,59))));

        }

        //검색 조건 페이징

        int page = Utils.onlyPositiveNumber(search.getPage(),1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(),20);

        Pageable pageable = PageRequest.of(page, limit, Sort.by(desc("createdAt")));
        Page<Reservation> data = reservationRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        return new ListData<>(data.getContent(), pagination);
    }
}
