package com.blooddonation.admin.reservation.controllers;

import com.blooddonation.admin.menus.Menu;
import com.blooddonation.admin.menus.MenuDetail;
import com.blooddonation.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/reservation")
public class ReservationController implements ExceptionProcessor {
    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "reservation";
    }
    @ModelAttribute
    public List<MenuDetail> getsubMenus() {
        return Menu.getMenus("reservation");
    }

    /**
     * 예약 현황 / 예약 관리
     * @return
     */
    @GetMapping
    public String list(Model model) {
        return "admin/reservation/list";
    }

    /**
     * 지점 등록
     * @param model
     * @return
     */
    @GetMapping("/add_branch")
    public String addBranch(Model model) {
        return "admin/reservation/add_branch";
    }

    @PostMapping("save_branch")
    public String saveBranch(Model model) {
        return "redirect:/admin/reservation/";
    }

}
