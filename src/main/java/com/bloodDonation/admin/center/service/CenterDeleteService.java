package com.bloodDonation.admin.center.service;

import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.repositories.CenterInfoRepository;
import com.bloodDonation.commons.exceptions.AlertException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bloodDonation.commons.Utils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterDeleteService {
    private final CenterInfoRepository centerInfoRepository;
    private final Utils utils;

    public void deleteList(List<Integer> chks){
        if(chks == null || chks.isEmpty()){
            throw new AlertException("삭제할 센터를 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for(int chk : chks){
            Long cCode = Long.valueOf(utils.getParam("cCode_" + chk));
            CenterInfo data = centerInfoRepository.findById(cCode).orElse(null);
            if(data == null) continue;

            centerInfoRepository.delete(data);
        }

        centerInfoRepository.flush();
    }
}
