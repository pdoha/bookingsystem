package com.bloodDonation.admin.center.controllers;

import com.bloodDonation.commons.ExceptionProcessor;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/admin/center")
public class CenterController implements ExceptionProcessor {

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "center";
    }

    /**
     * 센터 목록
     */
    @GetMapping
    public String list(Model model) {
        commonProcess("list", model);

        return "admin/center/list";
    }

    /**
     * 센터 등록 - 등록 & 수정 통합 개발
     * @param model
     * @return
     */
    @GetMapping("/add_center")
    public String addCenter(@ModelAttribute RequestCenter form, Model model) {
        commonProcess("add_center", model);

        return "admin/center/add_center";
    }

    /**
     * 센터 추가, 저장
     * @param model
     * @return
     */
    @PostMapping("/save_center")
    public String saveCenter(@Valid RequestCenter form, Errors errors, Model model) {
        String mode = form.getMode();

        commonProcess(mode, model);

        if (errors.hasErrors()) {
            return "admin/center/" + mode;
        }

        return "redirect:/admin/center/info_center";
    }

    /**
     * 센터 상세 정보
     * @param model
     * @return
     */
    @GetMapping("/info_center")
    public String infoCenter(Model model) {
        commonProcess("info_center", model);

        return "admin/center/info_center";
    }

    /**
     * 공통 처리
     *
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model) {
        String pageTitle = "헌혈의집 센터 목록";
        mode = Objects.requireNonNullElse(mode, "list");

        if (mode.equals("add_center")) {
            pageTitle = "새로운 센터 등록";

        } else if (mode.equals("edit_center")) {
            pageTitle = "센터 정보 수정";

        } else if (mode.equals("info_center")) {
            pageTitle = "센터 상세 정보";

        }

        model.addAttribute("pageTitle", pageTitle);
    }
}