package com.bloodDonation.admin.member.controllers;

import com.bloodDonation.admin.menus.Menu;
import com.bloodDonation.admin.menus.MenuDetail;
import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.member.Authority;
import com.bloodDonation.member.controllers.MemberSearch;
import com.bloodDonation.member.entities.Authorities;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.service.MemberInfo;
import com.bloodDonation.member.service.MemberInfoService;
import com.bloodDonation.member.service.MemberSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {

    private final MemberInfoService memberInfoService;
    private final MemberSaveService memberSaveService;

    private final ControlValidator controlValidator;
    private final UpdateValidator updateValidator;


    //주메뉴
    @ModelAttribute("menuCode")
    public String getMenuCode(){
        return "member";
    }

    // 서브 메뉴
    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus(){
        return Menu.getMenus("member");
    }


    //회원 목록 &  관리
    @GetMapping
    public String list(@ModelAttribute MemberSearch search, Model model){
        commonProcess("list", model);
        ListData<Member> data = memberInfoService.getList(search);
        //연동
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());



        return "admin/member/list";
    }

    //선택수정
    @PatchMapping
    public String editList(@RequestParam(name="chk", required = false) List<Long> chks, Model model) {
        return "common/_execute_script";


    }


    //회원 수정페이지
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") String userId, @ModelAttribute RequestUpdate form, Model model){
        commonProcess("edit", model);

        MemberInfo memberInfo = (MemberInfo)memberInfoService.loadUserByUsername(userId);
        Member member = memberInfo.getMember();

        List<Authority> authorities = member.getAuthorities() == null ? null
                                    : member.getAuthorities().stream()
                                    .map(Authorities::getAuthority).toList();

        form.setUserId(userId);
        form.setAuthorities(authorities);
        form.setEmail(member.getEmail());

        model.addAttribute("requestUpdate", form);
        return "admin/member/edit";
    }


    //이용 관리(권한)
    @GetMapping("/control/{userId}")
    public String control(@PathVariable("userId") String userId, @ModelAttribute RequestControl form, Model model){
        commonProcess("control", model);
        MemberInfo memberInfo = (MemberInfo) memberInfoService.loadUserByUsername(userId);

        //회원정보 가져와서
        Member member = memberInfo.getMember();

        //권한이 없으면 null,  있으면 이넘상수 authority만 뽑아서 가져옴
        List<Authority> authorities = member.getAuthorities() == null ? null
                                        : member.getAuthorities().stream()
                                        .map(Authorities::getAuthority).toList();
        //탈퇴처리, 권한 부여
        form.setUserId(userId);
        form.setEnable(member.isEnable());
        form.setAuthorities(authorities);

        return "admin/member/control";

    }

    //수정 처리
    @PostMapping("/control")
    public String controlPs(RequestControl form, Errors errors, Model model){

        controlValidator.validate(form, errors); //검증 에러메세지
        if(errors.hasErrors()){
            return "admin/member/control";
        }

        memberSaveService.updateControl(form);

        //탈퇴하면 목록 새로고침
        model.addAttribute("script", "parent.location.reload();");

        return "common/_execute_script";

    }

    //회원 추가 & 저장 (동시에 공유)
    @PostMapping("/save")
    public String save(@Valid RequestUpdate form, Errors errors, Model model){
        commonProcess(form.getMode(), model);

        updateValidator.validate(form, errors);

        if(errors.hasErrors()){
            //저장 실패시 수정목록 다시
            return "admin/member/edit";
        }

        //저장하면 회원목록으로 이동
        return "redirect:/admin/member";
    }

    //공통 처리 - 제목,
    private void commonProcess(String mode, Model model){
        String pageTitle = "회원목록";
        //모드값이 없을때는 회원목록 (list) 쪽으로 넘긴다
        mode = StringUtils.hasText(mode) ? mode : "list";

        //공통
        List<String> addCss = new ArrayList<>(); // CSS 추가

        //양식에 필요한 스크립트 ( 게시판 등록, 수정에 다 필요하니까 form)
        List<String> addScript = new ArrayList<>();

        //추가 등록 수정에 필요한 공통적인 자바스크립트 (관리자쪽에 필요한 설정
        List<String> addCommonScript = new ArrayList<>();


        addScript.add("member/common"); //관리자 회원수정

        if (mode.equals("control") || mode.equals("edit")){ //회원 이용관리 또는 수정일때

            pageTitle = mode.equals("edit") ? "회원 수정" : "이용관리";
            pageTitle += mode.contains("edit") ? "회원 수정" : "이용관리";
            addCommonScript.add("mName");
            addCommonScript.add("ckeditor5/ckeditor"); //에디터 추가
            addScript.add("member/form"); // 양식

            }




        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode); //서브메뉴코드는 모드값과 동일하게
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);


    }

}
