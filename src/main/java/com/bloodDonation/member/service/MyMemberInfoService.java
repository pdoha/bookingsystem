package com.bloodDonation.member.service;

import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.controllers.RequestJoin;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.MemberRepository;
import com.bloodDonation.mypage.controllers.RequestMemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
@RequiredArgsConstructor
public class MyMemberInfoService {

    private final MemberRepository memberRepository;
    private final MemberUtil memberUtil;
    public void myinfo(RequestJoin form) {
        if (!memberUtil.isLogin()) {
            return;
        }

        Member member = memberUtil.getMember();//memberutil에 session에 저장된 내 정보를 가져오는 getmember이용하여 member에 저장
        member = memberRepository.findByUserId(member.getUserId()).orElse(null);//기본값으로 널 넣기
        String mName = form.getMName();
        String userId = form.getUserId();
        String userPw = form.getUserPw();
        String email = form.getEmail();

        //혈액형하고, 전화번호는 회원가입할때는 필요없는 항목이라, 개인정보변경에서 입력한
        //혈액형과 전화번호는 따로 관리해서 null이 아니면 조회페이지에서 볼 수 있게 해야됨

        String zonecode = form.getZonecode();
        String address = form.getAddress();
        String addressSub = form.getAddressSub();


    }
}
