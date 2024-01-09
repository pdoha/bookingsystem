package com.blooddonation.member.controllers;

import com.blooddonation.commons.ExceptionProcessor;
import com.blooddonation.commons.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {
    private final Utils utils;

    //회원가입
    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form){
        return utils.tpl("member/join");
    }

    //회원가입 처리
    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors){

        if(errors.hasErrors()) {//참이면
            //입력한 값 데이터(template)를 그대로 보여준다 (수정할 수 있게)

            return utils.tpl("member/join");


        }

        //가입 완료 후 로그인페이지로 이동
        return "redirect:/member/login";

    }
}
