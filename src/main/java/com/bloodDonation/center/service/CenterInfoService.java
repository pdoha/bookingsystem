package com.bloodDonation.center.service;

import com.bloodDonation.center.entities.CenterInfo;
import com.bloodDonation.center.repositories.CenterInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


// 센터 목록 조회 서비스
@Service
@RequiredArgsConstructor
public class CenterInfoService {

    private final CenterInfoRepository repository;
    private final HttpServletRequest request;

    /**
     * 전체 센터 목록 조회
     */
    public List<CenterInfo> findCenters(){
        return repository.findAll();
    }

    /**
     * 센터 코드로 찾기
     * @param cCode
     * @return
     */
    public Optional<CenterInfo> findOne(Long cCode){
        return repository.findBycCode(cCode);
    }


    /**
     * 센터 목록 조회
     *
     * @param cCode
     * @param cName
     * @param address
     * @return

    public List<CenterInfo> getList(Long cCode, String cName, String address, String telNum, String operHour){
        QCenterInfo centerInfo = QCenterInfo.centerInfo;

        String[] city = address.split(" ");     //city[0] : 시도명(서울, 인천, 경기, ..)

        //센터코드, 센터명, 주소, 전화번호, 운영시간
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(centerInfo.cCode.eq(cCode));
        builder.and(centerInfo.cName.eq(cName));
        builder.and(centerInfo.address.eq(city));
        builder.and(centerInfo.telNum.eq(telNum));
        builder.and(centerInfo.operHour.eq(operHour));

        return
    }
     */
}
