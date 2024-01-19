package com.bloodDonation.admin.member.controllers;

import com.bloodDonation.admin.menus.Menu;
import com.bloodDonation.admin.menus.MenuDetail;
import com.bloodDonation.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController implements ExceptionProcessor {

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


    //회원 목록 &  관리 (레이어팝업)
    @GetMapping
    public String list(Model model){

        //연동
        commonProcess("list", model);
        return "admin/member/list";
    }

    //회원 등록
    @GetMapping("/add")
    public String add(Model model){
        commonProcess("add", model);
        return "admin/member/add";
    }
    //회원 추가 & 저장 (동시에 공유)
    @PostMapping("/save")
    public String save(Model model){
        //모드값이 필요해서 나중에 할게요

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


        if (mode.equals("add") || mode.equals("edit")){ //회원 등록 또는 수정일때
            addCommonScript.add("ckeditor5/ckeditor"); //에디터 추가
            addScript.add("member/form"); // 양식

        }

        if (mode.equals("add")){
            pageTitle = "회원 등록";

        }else if(mode.equals("edit")){
            pageTitle = "회원 수정";

        }




        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode); //서브메뉴코드는 모드값과 동일하게
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);


    }

}
