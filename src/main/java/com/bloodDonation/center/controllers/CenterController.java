package com.bloodDonation.center.controllers;

import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.service.CenterInfoService;
import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/center")
@RequiredArgsConstructor
public class CenterController implements ExceptionProcessor {

    private final CenterInfoService centerInfoService;
    private final Utils utils;

    private CenterInfo centerInfo;

    @GetMapping("/{cCode}")
    public String view(@PathVariable("cCode") Long cCode, Model model){
        commonProcess(cCode, "view", model);
        return utils.tpl("center/view");
    }
    private void commonProcess(String mode, Model model) {
        String pageTitle = "";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        addCss.add("center/style");

        if(mode.equals("view")){
            pageTitle = centerInfo.getCName();
            addCommonScript.add("map");
            addScript.add("center/view");
        }

        model.addAttribute("pageTitle", pageTitle);

        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
    }
    private void commonProcess(Long cCode, String mode, Model model){
        centerInfo = centerInfoService.get(cCode);
        commonProcess(mode, model);
        model.addAttribute("centerInfo", centerInfo);
    }
}
