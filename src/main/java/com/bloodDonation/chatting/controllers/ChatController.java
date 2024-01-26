package com.bloodDonation.chatting.controllers;

import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chatting")
@RequiredArgsConstructor
public class ChatController implements ExceptionProcessor {

    private final Utils utils;


    @GetMapping
    public String index(Model model) {
        commonProcess("main",model);
        return utils.tpl("chat/index");
    }

    private void commonProcess(String mode, Model model) {
        List<String> addCommonScript = new ArrayList<>();
        addCommonScript.add("chat");

        model.addAttribute("addCommonScript", addCommonScript);
    }
}
