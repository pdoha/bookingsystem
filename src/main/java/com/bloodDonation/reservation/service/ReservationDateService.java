package com.bloodDonation.reservation.service;

import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.service.CenterInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

//각 날짜가 예약 가능한지 가능하지 않은지 체크 (날짜 검증)
@Service
@RequiredArgsConstructor
public class ReservationDateService {
    private final CenterInfoService infoService;

    public boolean checkAvailable(Long cCode, String date) {

        //가져온 날짜가 가능한지 아닌지(가능 요일, 공휴일 체크)
        CenterInfo data = infoService.get(cCode);
        String bookYoil = data.getBookYoil();
        if (StringUtils.hasText(bookYoil)) {
            //예약가능 요일X
            return false;
        }


        return false;
    }
}
