package com.blooddonation.controllers.member;

import com.blooddonation.commons.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final Utils utils;

    @GetMapping("/join")
    //회원가입
    public String join(){

        //템플릿 연동
        //utils 라는 메소드를 따로만들어서 경로 앞에 front 나 mobile 안붙여도됨!
        return utils.tpl("member/join");

    }
}