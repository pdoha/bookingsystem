package com.bloodDonation.mypage.service;


import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.MemberRepository;
import com.bloodDonation.member.service.MemberInfo;
import com.bloodDonation.member.service.MemberInfoService;
import com.bloodDonation.mypage.controllers.RequestMemberInfo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final MemberUtil memberUtil;
    private final MemberInfoService memberInfoService;
    private final HttpSession session;

    //RequestMemberInfo--마이페이지-개인정보변경할 정보가 담긴 커맨드 객체
    public void update(RequestMemberInfo form) {
        if (!memberUtil.isLogin()) {
            return;
        }

        Member member = memberUtil.getMember();//memberutil에 session에 저장된 내 정보를 가져오는 getmember이용하여 member에 저장
        member = memberRepository.findByUserId(member.getUserId()).orElse(null);//기본값으로 널 넣기
        member.setMName(form.getMName());//회원명은 동명이인때문에 같은 이름인 사람이 여러명일수도 있음

        //주소-zonecode, address, addressSub도 수정하기로 함
        member.setZonecode(form.getZonecode());
        member.setAddress(form.getAddress());
        member.setAddressSub(form.getAddressSub());

        //비번 변경
        String userPw = form.getUserPw();
        if (StringUtils.hasText(userPw)) {
            member.setUserPw(encoder.encode(userPw));
        }

        //주소,비번 변경한거 저장하기
        memberRepository.saveAndFlush(member);

        MemberInfo memberInfo = (MemberInfo)memberInfoService.loadUserByUsername(member.getUserId());

        session.setAttribute("member", memberInfo.getMember());
    }
}
