package com.bloodDonation.reservation.service;

import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.service.CenterInfoService;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.commons.Pagination;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.reservation.controllers.RequestReservation;
import com.bloodDonation.reservation.controllers.ReservationSearch;
import com.bloodDonation.reservation.entities.QReservation;
import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import java.util.Arrays;
import java.util.Objects;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ReservationRepository reservationRepository;
    private final CenterInfoService centerInfoService;
    private final HttpServletRequest request;
    private final MemberUtil memberUtil;



    public Reservation get(Long bookCode) {
        Reservation reservation = reservationRepository.findById(bookCode).orElseThrow(ReservationNotFoundException::new);

        //추가작업 필요

        return reservation;
    }

    public RequestReservation getForm(Long bookCode) {
        Reservation reservation = get(bookCode);
        RequestReservation form = new ModelMapper().map(reservation, RequestReservation.class);

        form.setStatus(reservation.getStatus().name());
        form.setBookType(reservation.getBookType().name());

        LocalDateTime bookDateTime = reservation.getBookDateTime();
        form.setDate(bookDateTime.toLocalDate());
        form.setTime(bookDateTime.toLocalTime());
        form.setPersons(reservation.getCapacity());
        form.setCCode(reservation.getCenter().getCCode());
        form.setMember(reservation.getMember());

        return form;
    }

    public ListData<Reservation> getList(ReservationSearch search) {
        QReservation reservation = QReservation.reservation;

        BooleanBuilder andBuilder = new BooleanBuilder();

        //검색 조건 키워드들
        /*
        List<Long> memberSeq = search.getMemberSeq();
        List<Long> bookCode = search.getBookCode();
        List<String> userId = search.getUserId();
        List<String> userName = search.getUserName();
        List<String> center = search.getCenter();
         */

        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();

        /* 예약 기간 검색 S */
        if (sDate != null) {
            andBuilder.and(reservation.bookDateTime.after(LocalDateTime.of(sDate, LocalTime.of(0, 0, 0))));
        }

        if (eDate != null) {
            andBuilder.and(reservation.bookDateTime.before(LocalDateTime.of(eDate, LocalTime.of(23,59,59))));
        }
        /* 예약 기간 검색 E */

        String sopt = search.getSopt();
        String skey = search.getSkey();

        sopt = StringUtils.hasText(sopt) ? sopt : "all";
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();

            BooleanExpression bookCodeCond = reservation.bookCode.stringValue().contains(skey);
            BooleanExpression userIdCond = reservation.member.userId.contains(skey);
            BooleanExpression userNameCond = reservation.member.mName.contains(skey);
            BooleanExpression centerCond = reservation.center.cName.contains(skey);

            if(sopt.equals("bookCode")) {
                 andBuilder.and(bookCodeCond);

            } else if (sopt.equals("userId")) {
                BooleanBuilder orBuilder = new BooleanBuilder();
                andBuilder.and(orBuilder.or(userIdCond).or(userNameCond));
            } else if (sopt.equals("center")) {
                andBuilder.and(centerCond);
            } else {
                BooleanBuilder orBuilder = new BooleanBuilder();
                andBuilder.and(orBuilder.or(bookCodeCond).or(userIdCond).or(userNameCond).or(centerCond));
            }
        }




        /*
        if(sDate != null) {
            andBuilder.and(reservation.bookDateTime.goe(LocalDateTime.of(
                    sDate, LocalTime.of(0,0,0))));
        }

        if(eDate != null) {
            andBuilder.and(reservation.bookDateTime.loe(LocalDateTime.of(eDate, LocalTime.of(23,59,59))));

        }

         */

        //검색 조건 페이징

        int page = Utils.onlyPositiveNumber(search.getPage(),1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(),5);

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<Reservation> data = reservationRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        return new ListData<>(data.getContent(), pagination);
    }

    //마이페이지 회원 예약조회에 이용할 메서드! 
    public ListData<Reservation> getMyList(ReservationSearch search) {
        //로그인정보가 없으면 null 출력
        if (!memberUtil.isLogin()) {
            return null;
        }
        //로그인 정보가 있으면 가져와서 그 로그인한 회원의 userId에만 해당하는 예약 데이터출력
        search.setUserId(Arrays.asList(memberUtil.getMember().getUserId()));

        return getList(search);
    }

    public int getAvailableCapacity(Long cCode, LocalDateTime bookDateTime) {
        CenterInfo center = centerInfoService.get(cCode);
        Integer current = reservationRepository.getTotalCapacity(cCode, bookDateTime);
        int curr = Objects.requireNonNullElse(current, 0);

        return center.getBookCapacity() - curr;
    }

}
