package com.bloodDonation.center.service;

import com.bloodDonation.admin.center.controllers.RequestCenter;
import com.bloodDonation.center.entities.CenterInfo;
import com.bloodDonation.center.repositories.CenterInfoRepository;
import com.bloodDonation.commons.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CenterSaveService {

    private final CenterInfoRepository centerInfoRepository;
    private final HttpServletRequest request;
    private final Utils utils;
    public void save(RequestCenter form){

        String mode = form.getMode();
        Long seq = form.getCCode();
        String name = form.getCName();

        CenterInfo centerInfo = null;
/*
        if (mode.equals("edit") && seq != null) {
            // 이미 등록된 센터를 수정할 경우 센터코드를 통해 찾음
            centerInfo = centerInfoRepository.findBycCode(seq).orElseThrow(CenterInfoNotFoundException::new);
        } else {
            // 없던 센터를 등록하는 경우 새로운 객체 생성
            centerInfo = new CenterInfo();
            centerInfo.setCCode(form.getCCode());
        }

        List<CenterInfo> centers = new ArrayList<>();
        CenterInfo center = CenterInfo.builder()
                .cCode(seq)
                .cName()
                */

    }

}
