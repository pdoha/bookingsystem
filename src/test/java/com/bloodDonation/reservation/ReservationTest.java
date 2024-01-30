package com.bloodDonation.reservation;

import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.service.MemberInfoService;
import com.bloodDonation.member.service.MemberSaveService;
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
        Member member = new Member();
        member.setUserId("user01");
        member.setGid("응");
        member.setUserPw("aA123456");
        member.setEmail("user01@naver.com");
        member.setMName("이이름");

    }

}
