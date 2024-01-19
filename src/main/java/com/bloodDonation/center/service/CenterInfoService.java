package com.bloodDonation.center.service;

import com.bloodDonation.admin.center.controllers.RequestCenter;
import com.bloodDonation.center.controllers.CenterSearch;
import com.bloodDonation.center.entities.CenterInfo;
import com.bloodDonation.center.entities.QCenterInfo;
import com.bloodDonation.center.repositories.CenterInfoRepository;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.commons.Pagination;
import com.bloodDonation.commons.Utils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.data.domain.Sort.Order.desc;


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
        RequestCenter form = new ModelMapper().map(data, RequestCenter.class);

        String bookYoil = data.getBookYoil();
        if(StringUtils.hasText(bookYoil)){
            List<String> yoils = Arrays.stream(bookYoil.split(",")).toList();
            form.setBookYoil(yoils);
        }

        String bookAvl = data.getBookAvl();
        // 09:00-14:00
        // 정규표현식 사용하여 데이터 가공
        Pattern pattern = Pattern.compile("(\\d{2}):(\\d{2})-(\\d{2}):(\\d{2})");
        Matcher matcher = pattern.matcher(bookAvl);
        if(matcher.find()){
            form.setBookAvlShour(matcher.group(1));
            form.setBookAvlSmin(matcher.group(2));
            form.setBookAvlEhour(matcher.group(3));
            form.setBookAvlEmin(matcher.group(4));
        }
        form.setMode("edit_center");

        return form;
    }

    public ListData<CenterInfo> getList(CenterSearch search){
        int page = Utils.onlyPositiveNumber(search.getPage(),1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        BooleanBuilder andBuilder = new BooleanBuilder();
        QCenterInfo centerInfo = QCenterInfo.centerInfo;

        /* 검색 조건 처리 S */
        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt : "all";
        if(StringUtils.hasText(skey)){
            skey = skey.trim();

            BooleanExpression cNameCond = centerInfo.cName.contains(skey);
            BooleanExpression addressCond = centerInfo.address.contains(skey);
            BooleanExpression addressSubCond = centerInfo.addressSub.contains(skey);

            if(sopt.equals("cName")){
                andBuilder.and(cNameCond);
            } else if (sopt.equals("address")){
                BooleanBuilder orBuilder = new BooleanBuilder();
                andBuilder.and(orBuilder.or(addressCond).or(addressSubCond));
            } else {
                BooleanBuilder orBuilder = new BooleanBuilder();
                andBuilder.and(orBuilder.or(addressCond).or(addressSubCond).or(cNameCond));
            }
        }
        /* 검색 조건 처리 E */


        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<CenterInfo> data = centerInfoRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        return new ListData<>(data.getContent(), pagination);
    }

}
