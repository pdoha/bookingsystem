package com.bloodDonation.reservation;

import com.bloodDonation.member.controllers.RequestJoin;
import com.bloodDonation.member.service.MemberInfoService;
import com.bloodDonation.member.service.MemberSaveService;
import com.bloodDonation.reservation.controllers.RequestReservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(properties = "spring.profiles.active=test")
public class ReservationTest {

    @Autowired
    private MemberSaveService saveService;

    @Autowired
    private MemberInfoService infoService;

    @Test
    void saveTest() {
        RequestJoin join = new RequestJoin();
        join.setUserId("user01");
        join.setUserPw("aA123456");
        join.setEmail("user01@naver.com");
        join.setMName("이이름");
        join.setConfirmPassword("aA123456");
        join.setAgree(true);

    }

    @Test
    void reservationSaveTest() {
        RequestReservation add = new RequestReservation();
        add.
    }

}
