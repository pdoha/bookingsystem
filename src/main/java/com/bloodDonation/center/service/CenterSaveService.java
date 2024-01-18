package com.bloodDonation.center.service;

import com.bloodDonation.admin.center.controllers.RequestCenter;
import com.bloodDonation.center.entities.CenterInfo;
import com.bloodDonation.center.repositories.CenterInfoRepository;
import com.bloodDonation.commons.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CenterSaveService {

    private final CenterInfoRepository centerInfoRepository;

    private final HttpServletRequest request;
    private final Utils utils;
    public void save(RequestCenter form){

        String mode = form.getMode();
        Long cCode = form.getCCode();
        String name = form.getCName();

        mode = StringUtils.hasText(mode) ? mode : "add_center";     //기본값

        CenterInfo data = null;

        if(mode.equals("edit_center") && cCode != null) {
            data = centerInfoRepository.findBycCode(cCode).orElseThrow(CenterNotFoundException::new);
        } else {
            data = new CenterInfo();
        }

        data.setCName(form.getCName());
        data.setZonecode(form.getZonecode());
        data.setAddress(form.getAddress());
        data.setAddressSub(form.getAddressSub());
        data.setTelNum(form.getTelNum());
        data.setOperHour(form.getOperHour());

        List<String> bookYoil = form.getBookYoil();
        String bookYoilStr = bookYoil == null ? null : bookYoil.stream().collect(Collectors.joining(","));
        data.setBookYoil(bookYoilStr);

        String bookAvl = String.format("%s:%s-%s:%s",
                form.getBookAvlShour(),
                form.getBookAvlSmin(),
                form.getBookAvlEhour(),
                form.getBookAvlEmin());
        data.setBookAvl(bookAvl);

        data.setBookNotAvl(form.getBookNotAvl());
        data.setBookHday(form.isBookHday());
        data.setBookBlock(form.getBookBlock());
        data.setBookCapacity(form.getBookCapacity());

        centerInfoRepository.saveAndFlush(data);
    }

}
