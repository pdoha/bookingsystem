package com.bloodDonation.member.service;

import com.bloodDonation.commons.ListData;
import com.bloodDonation.commons.Pagination;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.member.controllers.MemberSearch;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.entities.QMember;
import com.bloodDonation.member.repositories.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static org.springframework.data.domain.Sort.Order.desc;

//회원조회서비스
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final HttpServletRequest request;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username) //이메일 조회
                .orElseGet(() -> memberRepository.findByUserId(username) //아이디로 조회
                        .orElseThrow(() -> new UsernameNotFoundException(username)));
        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getUserPw())
                .member(member)
                .build();
    }

    public ListData<Member> getList(MemberSearch search) {
        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        QMember member = QMember.member;
        BooleanBuilder andBuilder = new BooleanBuilder();

        /* 검색어 처리 S */
        String sopt = search.getSopt();
        String skey = search.getSkey();

        sopt = StringUtils.hasText(sopt) ? sopt : "all";
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();

            BooleanExpression userIdConds = member.userId.contains(skey);
            BooleanExpression mNameConds = member.mName.contains(skey);
            BooleanExpression emailConds = member.email.contains(skey);
            if (sopt.equals("userId")) { // 아이디 검색
                andBuilder.and(userIdConds);
            } else if (sopt.equals("mName")) { // 회원명
                andBuilder.and(mNameConds);
            } else if (sopt.equals("email")) { // 이메일
                andBuilder.and(emailConds);
            } else { // 통합검색
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(userIdConds).or(mNameConds).or(emailConds);
                andBuilder.and(orBuilder);
            }
        }


        /* 검색어 처리  E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<Member> data = memberRepository.findAll(andBuilder, pageable);

        int total = (int)memberRepository.count(andBuilder);

        Pagination pagination = new Pagination(page, total, 10, limit, request);

        return new ListData<>(data.getContent(), pagination);
    }
}
