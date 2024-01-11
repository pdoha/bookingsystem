package com.bloodDonation.member;

import com.bloodDonation.member.entities.Authorities;
import com.bloodDonation.member.entities.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//메세지 부분 일괄로 지울 수 있는 메서드
public class MemberUtil {
    //현재 로그인 세션 비우는 것
    private final HttpSession session;
    /*이거 오류나서 나중에 할께요
    public boolean isAdmin(){
        if(isLogin()){
            return getMember().getAuthorities()
                    .stream().map(Authorities::getAuthority)
                    .anyMatch(a->a == Authority.ADMIN);
        }
        return false;
    }
    */

    /*getMember()를 활용해서 멤버를 가져올 수 있다면 로그인 상태*/
    public boolean isLogin(){
        return getMember() != null;
    }
    /*세션에 저장된 멤버를 가져오는 것*/
    public Member getMember(){
        Member member = (Member) session.getAttribute("member");
        return member;
    }
    public static void clearLoginData(HttpSession session){
        //userId 와 메세지 지우기
        session.removeAttribute("userId");
        session.removeAttribute("NotBlank_userId");
        session.removeAttribute("NotBlank_userPw");
        session.removeAttribute("Global_error");

    }

}
