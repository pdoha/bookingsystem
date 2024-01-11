package com.bloodDonation.member.controllers;

import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.member.service.JoinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {
    private final Utils utils;
    private final JoinService joinService;

    //회원가입
    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form,
                       @RequestParam(name="redirectURL", required = false) String redirectURL, Model model){
        //주소 넘어오면 양식에 추가 ( model을 통해)
        model.addAttribute("redirectURL", redirectURL);
        return utils.tpl("member/join");
    }

    //회원가입 처리
    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors){
        joinService.process(form, errors);

        //가입 실패
        if(errors.hasErrors()) {//참이면
            //입력한 값 데이터(template)를 그대로 보여준다 (수정할 수 있게)

            return utils.tpl("member/join");


        }
        //가입 성공
        //가입 완료 후 로그인페이지로 이동
        return "redirect:/member/login";

    }

    @GetMapping("/login")
    public String login() {

        return utils.tpl("member/login");
    }
}
