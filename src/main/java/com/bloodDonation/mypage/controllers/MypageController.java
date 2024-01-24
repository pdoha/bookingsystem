package com.bloodDonation.mypage.controllers;


import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.mypage.service.MemberUpdateService;
import com.bloodDonation.mypage.service.MyPageModifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController implements ExceptionProcessor {

    private final Utils utils;
    private final MyPageModifyService service;
    private final MemberUtil memberUtil;
    private final MemberUpdateService updateService;
    @ModelAttribute("addCss")
    public String[] getAddCss() {

        return new String[] { "mypage/style" };
    }

    @ModelAttribute("addScript")
    public String[] getAddScript() {

        return new String[] { "mypage/common"};
    }

    /**
     * 마이페이지 첫 화면
     *
     * @param model
     * @return
     */
    @GetMapping
    public String index(Model model) {
        commonProcess("main", model);

        return utils.tpl("mypage/index");
    }

    @GetMapping("/info")
    public String info(Model model) {
        commonProcess("info", model);


        return utils.tpl("mypage/info");
    }

    @GetMapping("/modify")
    //RequestMemberInfo로 변경
    public String modify(@ModelAttribute RequestMemberInfo form, Model model) {
        commonProcess("modify", model);
    //로그인 되었을때
        if (memberUtil.isLogin()) {
            Member member = memberUtil.getMember();
            form = new ModelMapper().map(member, RequestMemberInfo.class);
        }

        model.addAttribute("requestMemberInfo", form);

        return utils.tpl("mypage/modify");
    }

    @PostMapping("/modify222")
    public String modifiy22(@RequestBody Member member) {
        service.modifyMyInfo(member);
        return utils.tpl("mypage/modify");
    }

    @PostMapping("/modify")
    public String modifyPs(@Valid RequestMemberInfo form, Model model) {
        commonProcess("modify", model);
        //변경된 개인정보가 requestjoin에 저장되야됨-null체크 필수?
        updateService.update(form);

        return "redirect:/mypage/info";
    }
    @GetMapping("/reservation")
    public String reservation(Model model){
        commonProcess("reservation", model);
        return utils.tpl("mypage/reservation");
    }

     @GetMapping("/reservation/modify")
     public String reservationModify(Model model){
            commonProcess("reservation/modify",model);
            return utils.tpl("mypage/reservation_modify");
     }

    @GetMapping("/survey")
    public String survey(Model model){
        commonProcess("survey", model);
        return utils.tpl("mypage/survey");
    }
    /*
    @GetMapping("/dosurvey")
    public String dosurvey(Model model){
        commonProcess("dosurvey", model);
        return utils.tpl("mypage/dosurvey");
    }
    @GetMapping("/dosurvey_last")
    public String dosurveylast(Model model){
        commonProcess("dosurvey", model);
        return utils.tpl("mypage/dosurvey_last");
    }


    //설문조사-결과는 surveyresult.html에 보여줘야 됨 결과페이지는 get으로매핑
    //결과를 보여주기 전에 저장하는 건 postmapping?
    //전자문진 결과도 커맨드, 엔티티 필요?

    //검사결과는 전자문진 완료하면 볼 수 있게-나의 전자문진시 입력한 정보 보여주면서
    //날짜 조회추가해서?
    //검사결과도 바로보여주는게 아니라 검사결과페이지에서 결과보기버튼을 누르면 연결되게
    @GetMapping("/surveyresult")
    public String surveyResult(Model model){
        commonProcess("surveyresult", model);
        return utils.tpl("mypage/surveyresult");
    }
     */

    @GetMapping("/bloodview")
    public String bloodview(Model model){
        commonProcess("bloodview", model);
        return utils.tpl("mypage/bloodview");
    }
    @GetMapping("/myprint")
    public String myprint(Model model){
        //commonProcess("myprint", model);
        return utils.tpl("mypage/myprint");
    }
    /*
    @GetMapping("/unregister")
    public String unregister(@ModelAttribute RequestUnRegister form, Model model){
        commonProcess("unregister", model);
        return utils.tpl("mypage/unregister");
    }

    @PostMapping("/unregister")
    public String unreggisterPs(@Valid RequestUnRegister form, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return utils.tpl("mypage/unregister");
        }

        return "redirect:/";//탈퇴 후 메인페이지로
    }
    */
    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "main");
        String pageTitle = Utils.getMessage("마이페이지", "commons");

        List<String> addCommonScript = new ArrayList<>();

        if (mode.equals("info")) {
            pageTitle = Utils.getMessage("개인정보_조회", "commons");
        } else if (mode.equals("modify")) {
            pageTitle = Utils.getMessage("개인정보_변경", "commons");
            addCommonScript.add("address");

        } else if (mode.equals("reservation")) {
            pageTitle = Utils.getMessage("예약조회", "commons");
        } else if (mode.equals("reservation/modify")) {
            pageTitle = Utils.getMessage("예약변경", "commons");


        } else if (mode.equals("survey")) {
                pageTitle = Utils.getMessage("전자문진안내", "commons");

        } else if (mode.equals("bloodview")) {
            pageTitle = Utils.getMessage("나의_헌혈내역", "commons");
        } else if (mode.equals("myprint")) {
            pageTitle = Utils.getMessage("헌혈증서출력", "commons");
        } else if (mode.equals("dosurvey")) {
            pageTitle = Utils.getMessage("전자문진", "commons");
        } else if (mode.equals("surveyresult")) {
            pageTitle = Utils.getMessage("검사결과", "commons");

        } else {
            if(mode.equals("unregister")) {
                pageTitle = Utils.getMessage("회원탈퇴", "commons");
            }
        }


            model.addAttribute("pageTitle", pageTitle);
    }

}
