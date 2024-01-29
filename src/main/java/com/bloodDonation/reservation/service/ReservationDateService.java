package com.bloodDonation.reservation.service;

import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.service.CenterInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

//각 날짜가 예약 가능한지 가능하지 않은지 체크 (날짜 검증)
@Service("rDateService")
@RequiredArgsConstructor
public class ReservationDateService {

    private final CenterInfoService infoService;

    public boolean checkAvailable(Long cCode, String bookDate) {

        //가져온 날짜가 가능한지 아닌지(가능 요일, 공휴일 체크)
        CenterInfo data = infoService.get(cCode);
        String bookYoil = data.getBookYoil();
        if (!StringUtils.hasText(bookYoil)) {
            //예약가능 요일X
            return false;
        }

        LocalDate date = LocalDate.parse(bookDate);//문자열을 다시 LocalDate로

        //오늘 이전 날짜면 선택불가
        if(!date.isAfter(LocalDate.now())) {
            return false;
        }

        String yoil = date.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN); //요일 정보를 문자열로 변환

        if(!bookYoil.contains(yoil)) { //요일이 포함되어 있지 않으면 false
            return false;
        }

        //공휴일 예약 불가이면 공휴일도 체크
        if(!data.isBookHday()) { //공휴일도 예약 불가

        }


        return true; //기본값(검증 실패시에만 false
    }

    /**
     * 예약블록 설정
     */
    public List<LocalTime> getAvailableTimes(Long cCode, LocalDate date) {
        List<LocalTime> times = new ArrayList<>();

        CenterInfo centerInfo = infoService.get(cCode);

        int bookBlock = centerInfo.getBookBlock(); //지정한 예약블록 가져옴
        //예약 가능 시간
        String bookAvl = centerInfo.getBookAvl();
        String bookNotAvl = centerInfo.getBookNotAvl();

        //신청 가능 인원수 ( 무제한= -1 , 신청 불가: 0)
        int capacity = centerInfo.getBookCapacity();
        if(StringUtils.hasText(bookAvl) && capacity !=0 ) {
            //예약불가 부분 처리
            List<LocalTime[]> bookNotAvls = StringUtils.hasText(bookNotAvl) ?
                    Arrays.stream(bookNotAvl.trim().split("\\n"))
                            .map(s -> s.trim().replaceAll("\\r", ""))
                            .map(s -> s.split("-"))
                            .map(s -> new LocalTime[] { LocalTime.parse(s[0]), LocalTime.parse(s[1])})
                            .toList() : null;

            String[] bookAvls = bookAvl.split("-");
            LocalTime sTime = LocalTime.parse(bookAvls[0]);
            LocalTime eTime = LocalTime.parse(bookAvls[1]);

            times.add(sTime);

            LocalTime tmpTime = sTime;
            while(true) {
                LocalTime newTime = tmpTime.plusMinutes(bookBlock); //시작시간 + 예약블록 시간만큼
                tmpTime = newTime;
                if ( newTime.equals(eTime) || newTime.isAfter(eTime)) { //종료시간까지만
                    break;
                }

                //예약 불가 블록에 있는 경우 배제
                if(bookNotAvls != null) { //예약불가시간 시작 <= X <= 예약불가 끝시간이면 배제
                    boolean notAvailable = bookNotAvls.stream()
                            .anyMatch(t -> newTime.equals(t[0])
                                            || newTime.isAfter(t[0])
                                            && newTime.isBefore(t[1]));
                    if(notAvailable) {
                        continue;
                    }
                }

                /**
                 * 신청 인원수: 예약 테이블에서 체크 -> 예약가능한 인원수가 없으면 continue;
                 */
                times.add(newTime);
            }
        }

        return times;
    }
}
