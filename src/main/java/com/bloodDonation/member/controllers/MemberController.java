package com.bloodDonation.member.controllers;

import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.service.FindPwService;
import com.bloodDonation.member.service.JoinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@SessionAttributes("EmailAuthVerified") //이메일 인증 여부 세션값 초기 처리
public class MemberController implements ExceptionProcessor {
    private final Utils utils;
    private final JoinService joinService;
    private final MemberUtil memberUtil;
    private final FindPwService findPwService; //비밀번호찾기


    //회원가입
    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form, Model model){
        commonProcess("join", model);
        //이메일 인증 여부 false로 초기화
        model.addAttribute("EmailAuthVerified", false);


        return utils.tpl("member/join");
    }

    //회원가입 처리
    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors, Model model, SessionStatus sessionStatus){
        commonProcess("join", model);

        //EmailAuthVerified 세션값 비우기
        sessionStatus.setComplete();

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

    //회원조회
    public void info(){
        if(memberUtil.isLogin()){
            Member member = memberUtil.getMember();
            System.out.println(member);
        }else{
            System.out.println("미로그인 상태");
        }
    }


    @GetMapping("/login")
    public String login(Model model) {
        commonProcess("login", model);

        return utils.tpl("member/login");
    }

    //비밀번호 찾기 양식
    @GetMapping("/find_pw")
    public String findPw(@ModelAttribute RequestFindPw form, Model model){
        commonProcess("find_pw", model);

        return utils.tpl("member/find_pw");
    }

    //비밀번호 찾기 처리
    @PostMapping("/find_pw")
    public String findPwPs(@Valid RequestFindPw form, Errors errors, Model model){
        commonProcess("find_pw", model);

        //비밀번호 찾기 처리
        findPwService.process(form, errors);

        if(errors.hasErrors()){
            return utils.tpl("member/find_pw");
        }

        //비밀번호 찾기에 이상없다면 완료 페이지로 이동
        return "redirect:/member/find_pw_done";
    }

    //비밀번호 찾기 완료 페이지
    @GetMapping("/find_pw_done")
    public String findPwDone(Model model){
        commonProcess("find_pw", model);

        return utils.tpl("member/find_pw_done");
    }


    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "join";
        String pageTitle = Utils.getMessage("회원가입", "commons");

        List<String> addCss = new ArrayList<>(); // CSS 추가
        List<String> addCommonScript = new ArrayList<>(); // 공통 자바스크립트
        List<String> addScript = new ArrayList<>(); // 프론트 자바스크립트

        if (mode.equals("login")) { //로그인
            pageTitle = Utils.getMessage("로그인", "commons");
            addCss.add("member/style");
            addScript.add("member/common");

        } else if (mode.equals("join")) { //회원가입
            addCss.add("member/join");
            addCommonScript.add("fileManager");
            addCommonScript.add("address");
            addScript.add("member/join");
            addScript.add("member/form"); //이메일 js
        } else if (mode.equals("find_pw")) { //비밀번호 찾기
            pageTitle = Utils.getMessage("비밀번호_찾기", "commons");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }
}
