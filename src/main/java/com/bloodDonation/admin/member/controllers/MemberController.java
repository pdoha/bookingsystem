package com.bloodDonation.admin.member.controllers;

import com.bloodDonation.admin.menus.Menu;
import com.bloodDonation.admin.menus.MenuDetail;
import com.bloodDonation.commons.ExceptionProcessor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController implements ExceptionProcessor {


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
    public String list(Model model){
        model.addAttribute("subMenuCode", "list");
        //연동
        commonProcess("list", model);
        return "admin/member/member_list";
    }

    //회원 등록 & 수정
    @GetMapping("/add_member")
    public String addMember(Model model){
        commonProcess("add_member", model);
        return "admin/member/add_branch";
    }
    //회원 추가 & 저장
    @PostMapping("/save_member")
    public String saveMember(Model model){
        //모드값이 필요해서 나중에 할게요

        //저장하면 회원목록으로 이동
        return "redirect:/admin/member/list";
    }

    //공통 처리 - 제목,
    private void commonProcess(String mode, Model model){
        String pageTitle = "회원목록";
        //모드값이 없을때는 회원목록 (list) 쪽으로 넘긴다
        mode = Objects.requireNonNullElse(mode, "list");

        if(mode.equals("add_member")){
            pageTitle = "회원등록";

        }else if(mode.equals("edit_member")){
            pageTitle = "회원수정";

        }else if ( mode.equals("list")){
            pageTitle = "회원목록";
        }

        model.addAttribute("pageTitle", pageTitle);
        //서브메뉴코드는 모드값과 동일하게
        model.addAttribute("subMenuCode", mode);


    }

}
