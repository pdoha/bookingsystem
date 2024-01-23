package com.bloodDonation.main.controllers;


import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class MainController implements ExceptionProcessor {


    private final Utils utils;

    @ModelAttribute("addCss")
    public String[] addCss(){
        return new String[] {"main/style"};
    }

    @ModelAttribute("addScript")
    public String[] addScript() {
        return new String[] { "main/common" };
    }

    @GetMapping("/")
    public String index(){

        return utils.tpl("main/index");

    }

}
