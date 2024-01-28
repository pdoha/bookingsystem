package com.bloodDonation.admin.member.controllers;

import com.bloodDonation.admin.menus.Menu;
import com.bloodDonation.admin.menus.MenuDetail;
import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.member.controllers.MemberSearch;
import com.bloodDonation.member.controllers.RequestJoin;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.service.MemberInfo;
import com.bloodDonation.member.service.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {

    private final MemberInfoService memberInfoService;

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

        ListData<Member> data = memberInfoService.getList(search);
        //연동
        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());
        commonProcess("list", model);


        return "admin/member/list";
    }

    //선택수정
    @PatchMapping
    public String editList(@RequestParam(name="chk", required = false) List<Long> chks, Model model) {
        return "common/_execute_script";


    }

    //회원 등록
    @GetMapping("/add")
    public String add(Model model){
        commonProcess("add", model);
        return "admin/member/add";
    }
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") String userId, Model model){
        commonProcess("edit", model);

        MemberInfo memberInfo = (MemberInfo)memberInfoService.loadUserByUsername(userId);
        RequestJoin member = new ModelMapper().map(memberInfo.getMember(), RequestJoin.class);

        model.addAttribute("requestJoin", member);
        return "admin/member/edit";
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
            addScript.add("member/manager"); //관리자 회원수정

        }

        if (mode.equals("add") || (mode.equals("edit"))){
            if (mode.equals("add")){
                pageTitle = "회원 등록";
                pageTitle += mode.contains("edit") ? "수정" : "등록";
                addCommonScript.add("mName");

            }

        }else if(mode.equals("edit")){
            pageTitle = "회원 수정";

        }




        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode); //서브메뉴코드는 모드값과 동일하게
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);


    }

}
