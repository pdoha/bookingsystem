package com.bloodDonation.center.service;

import com.bloodDonation.admin.center.controllers.RequestCenter;
import com.bloodDonation.center.entities.CenterInfo;
import com.bloodDonation.center.repositories.CenterInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


// 센터 목록 조회 서비스
@Service
@RequiredArgsConstructor
public class CenterInfoService {

    private final CenterInfoRepository centerInfoRepository;  // repository에게 DB관리 넘기므로 repository를 DI해줌
    private final HttpServletRequest request;


    public CenterInfo get(Long cCode){
        CenterInfo data = centerInfoRepository.findById(cCode).orElseThrow(CenterNotFoundException::new);

        return data;
    }

    public RequestCenter getForm(Long cCode) {
        CenterInfo data = get(cCode);
        RequestCenter form = new modelMapper().map(data, RequestCenter.class);

        String bookYoil = data.getBookYoil();
        if(StringUtils.hasText(bookYoil)){
            List<String> yoils = Arrays.stream(bookYoil.split(",")).toList();
            form.setBookYoil(yoils);
        }
    }
    /**
     * 전체 센터 목록 조회
     */
    public List<CenterInfo> findCenters(){
        return this.centerInfoRepository.findAll();
    }

    /**
     * 센터 코드로 찾기
     * @param cCode
     * @return
     */
    public Optional<CenterInfo> findOne(Long cCode){
        return centerInfoRepository.findBycCode(cCode);
    }

}
