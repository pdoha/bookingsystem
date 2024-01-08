package com.blooddonation.controllers.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/join")
    //회원가입
    public String join(){

        //템플릿 연동
        return "front/member/join";

    }
}