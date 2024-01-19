package com.bloodDonation.admin.controllers;


import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.member.MemberUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminMainController")
@RequestMapping("/admin")
public class MainController implements ExceptionProcessor {
    private final MemberUtil memberutil;

    public MainController(MemberUtil memberutil) {
        this.memberutil = memberutil;
    }

    @GetMapping
    public String index() {
        return "/admin/main/index";
    }
}
