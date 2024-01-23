package com.bloodDonation.admin.config.controllers;

import com.bloodDonation.admin.config.service.ConfigInfoService;
import com.bloodDonation.admin.config.service.ConfigSaveService;
import com.bloodDonation.admin.menus.Menu;
import com.bloodDonation.admin.menus.MenuDetail;
import com.bloodDonation.commons.ExceptionProcessor;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class BasicConfigController implements ExceptionProcessor {

    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;

//model : 뷰에서 사용할 데이터
    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "config";
    }

    @ModelAttribute("subMenuCode")
    public String getSubMenuCode(){
        return "basic";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus(){
        return Menu.getMenus("config");
    }

    @ModelAttribute("pageTitle")
    public String getPageTitle() {
        return "기본설정";
    }

    @GetMapping

    public String index(Model model) {

        BasicConfig config = infoService.get("basic",BasicConfig.class).orElseGet(BasicConfig::new);

        model.addAttribute("basicConfig",config);

        return "admin/config/basic";
    }

    @PostMapping
    public String save(BasicConfig config, Model model) {//저장하고 메시지 출력

        saveService.save("basic",config);

        model.addAttribute("message","저장되었습니다.");

        return "admin/config/basic";
    }
}
