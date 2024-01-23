package com.bloodDonation.admin.center.service;

import com.bloodDonation.admin.center.controllers.RequestCenter;
import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.repositories.CenterInfoRepository;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.exceptions.AlertException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CenterSaveService {

    private final CenterInfoRepository centerInfoRepository;
    private final Utils utils;

    public CenterInfo save(RequestCenter form){

        String mode = form.getMode();
        Long cCode = form.getCCode();

        mode = StringUtils.hasText(mode) ? mode : "add_center";     // mode 기본값

        CenterInfo data = null;

        if(mode.equals("edit_center") && cCode != null) {
            // 수정이면 원래 저장된 데이터 가져오기
            data = centerInfoRepository.findBycCode(cCode).orElseThrow(CenterNotFoundException::new);
        } else {
            // 수정 아니면 새로 생성
            data = new CenterInfo();
        }

        data.setCName(form.getCName());
        data.setZonecode(form.getZonecode());
        data.setAddress(form.getAddress());
        data.setAddressSub(form.getAddressSub());
        data.setTelNum(form.getTelNum());
        data.setOperHour(form.getOperHour());

        // 요일 가공: 월, 화, 수, ...
        List<String> bookYoil = form.getBookYoil();
        String bookYoilStr = bookYoil == null ? null : bookYoil.stream().collect(Collectors.joining(","));
        data.setBookYoil(bookYoilStr);

        // 예약 가능 시간 가공: 09:00-14:00
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

        return data;
    }

    /**
     * 목록 수정
     * @param chks
     */
    public void saveList(List<Integer> chks){
        if(chks == null || chks.isEmpty()){
            throw new AlertException("수정할 센터를 선택하세요.", HttpStatus.BAD_REQUEST);
        }
        for(int chk : chks){
            Long cCode = Long.valueOf(utils.getParam("cCode_" + chk));
            CenterInfo data = centerInfoRepository.findById(cCode).orElse(null);
            if(data == null) continue;

            String bookYoil = Arrays.stream(utils.getParams("bookYoil_" + chk)).collect(Collectors.joining(",")); //월, 화, 수
            data.setBookYoil(bookYoil);

            String bookAvl = String.format("%s:%s-%s:%s",
                    utils.getParam("bookAvlShour_" + chk),
                    utils.getParam("bookAvlSmin_" + chk),
                    utils.getParam("bookAvlEhour_" + chk),
                    utils.getParam("bookAvlEmin_" + chk));
            data.setBookAvl(bookAvl);

            boolean bookHday = Boolean.parseBoolean(utils.getParam("bookHday_" + chk));
            data.setBookHday(bookHday);

            int bookBlock = Integer.parseInt(utils.getParam("bookBlock_" + chk));
            data.setBookBlock(bookBlock);
        }

        centerInfoRepository.flush();
    }
}
