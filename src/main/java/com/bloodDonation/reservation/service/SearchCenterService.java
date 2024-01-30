package com.bloodDonation.reservation.service;

import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.entities.QCenterInfo;
import com.bloodDonation.admin.center.repositories.CenterInfoRepository;
import com.bloodDonation.admin.center.service.CenterNotFoundException;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.commons.Pagination;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.reservation.controllers.RCenterSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.codehaus.groovy.runtime.StreamGroovyMethods;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

//예약 1단계 - 센터 검색 서비스
@Service
@RequiredArgsConstructor
public class SearchCenterService {
    private final CenterInfoRepository centerInfoRepository;
    private final HttpServletRequest request;

    public CenterInfo get(Long cCode){
        CenterInfo data = centerInfoRepository.findById(cCode).orElseThrow(CenterNotFoundException::new);

        return data;
    }


    public ListData<CenterInfo> getList(RCenterSearch search){
        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        BooleanBuilder andBuilder = new BooleanBuilder();
        QCenterInfo centerInfo = QCenterInfo.centerInfo;

        /* 검색 조건 처리 S */
        // 평일, 주말, 공휴일, 18시 이후
        boolean weekday = search.isWeekday();
        boolean weekend = search.isWeekend();
        boolean bookHday = search.isBookHday();
        boolean bookAfter18 = search.isBookAfter18();







        // 시도군구


        //키워드 검색
        //String sopt = search.getSopt();     //키워드 검색 옵션
        String skey = search.getSkey();     //키워드
        //sopt = StringUtils.hasText(sopt) ? sopt : "all";

        if(StringUtils.hasText(skey)){
            BooleanExpression cNameCond = centerInfo.cName.contains(skey.trim());
            andBuilder.and(cNameCond);
        }

        /* 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<CenterInfo> data = centerInfoRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        return new ListData<>(data.getContent(), pagination);
    }
}
