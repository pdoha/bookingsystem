package com.bloodDonation.admin.reservation.controllers;

import com.bloodDonation.admin.center.controllers.CenterSearch;
import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.service.CenterInfoService;
import com.bloodDonation.admin.menus.Menu;
import com.bloodDonation.admin.menus.MenuDetail;
import com.bloodDonation.calendar.Calendar;
import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.reservation.controllers.RequestReservation;
import com.bloodDonation.reservation.controllers.ReservationMainValidator;
import com.bloodDonation.reservation.controllers.ReservationSearch;
import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.service.ReservationApplyService;
import com.bloodDonation.reservation.service.ReservationDeleteService;
import com.bloodDonation.reservation.service.ReservationInfoService;
import com.bloodDonation.reservation.service.ReservationSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller("adminReservationController")
@RequestMapping("/admin/reservation")
@RequiredArgsConstructor
public class ReservationController implements ExceptionProcessor {

    private final ReservationInfoService infoService;
    private final ReservationSaveService saveService;
    private final ReservationDeleteService deleteService;
    private final ReservationMainValidator validator;
    private final ReservationApplyService reservationApplyService;
    private final CenterInfoService centerInfoService;

    private final Calendar calendar;

    //주메뉴 불러오기
    @ModelAttribute("menuCode")
    public String getMenuCode(){
        return "reservation";
    }

    //서브메뉴 불러오기
    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {
        return Menu.getMenus("reservation");
    }
    @ModelAttribute("centerList")
    public List<CenterInfo> getCenterList() {
        CenterSearch search = new CenterSearch();
        search.setLimit(10000);
        ListData<CenterInfo> data = centerInfoService.getList(search);

        return data.getItems();
    }

    /**
     * 예약 현황, 예약 관리
     * @param model
     * @return
     */
    @GetMapping
    public String list(@ModelAttribute ReservationSearch search, Model model) {
        commonProcess("list",model);
        ListData<Reservation> data = infoService.getList(search);

        model.addAttribute("items",data.getItems());
        model.addAttribute("pagination",data.getPagination());

        return "admin/reservation/list";
    }


    /**
     * 리스트 예약 수정
     * @param chks
     * @param model
     * @return
     */
    @PatchMapping
    public String editList(@RequestParam(name="chk", required =false) List<Integer> chks, Model model) {
        commonProcess("list", model);

        saveService.saveList(chks);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }


    /**
     * 리스트 예약 삭제
     * @param chks
     * @param model
     * @return
     */
    @DeleteMapping
    public String deleteList(@RequestParam(name="chk", required = false) List<Integer> chks, Model model) {
        commonProcess("list", model);

        deleteService.deleteList(chks);

        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    /**
     * 예약 수정
     * @param bookCode
     * @param model
     * @return
     */
    @GetMapping("/edit/{bookCode}")
    public String edit(@PathVariable("bookCode") Long bookCode, Model model) {
        commonProcess("edit", model);

        RequestReservation form = infoService.getForm(bookCode);
        model.addAttribute("requestReservation", form);

        CenterInfo center = centerInfoService.get(form.getCCode());
        model.addAttribute("center", center);

        return "admin/reservation/edit";
    }



/**
 * 예약 등록
 * @param model
 * @return
 */
        @GetMapping("/add_reservation")
        public String addReservation(RequestReservation form, Errors errors, Model model) {
            commonProcess("add_reservation",model);
            Map<String, Object> data = calendar.getData(form.getYear(), form.getMonth());
            model.addAllAttributes(data);

            //추가작업
            return "admin/reservation/add_reservation";
        }


    @PostMapping("/apply")
    public String apply(RequestReservation form, Errors errors, Model model) {
        form.setMode("admin_add"); // 검증 -> valiateStep1, validateStep2
        validator.validate(form, errors);

        if (errors.hasErrors()) {

            Map<String, Object> data = calendar.getData(form.getYear(), form.getMonth());
            model.addAllAttributes(data);

            return "admin/reservation/add";
        }

        reservationApplyService.apply(form);

        return "redirect:/admin/reservation";
    }

    /**
     * 예약 저장
     * @param model
     * @return
     */
    @PostMapping("/save_reservation")
    public String saveReservation(@Valid RequestReservation form, Errors errors, Model model) {
        commonProcess("edit", model);

        if (errors.hasErrors()) {
            return "admin/reservation/edit";
        }

        saveService.save(form);

        return "redirect:/admin/reservation";
    }

    @GetMapping("/calendar")
    public String getCalendar(@ModelAttribute RequestReservation form, Model model) {

        Map<String, Object> data = calendar.getData(form.getYear(), form.getMonth());
        model.addAllAttributes(data);

        return "admin/reservation/_calendar";
    }


    /**
     * 공통처리
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model) {
        String pageTitle = "헌혈 예약자 목록";
        //기본값 = list
        mode = Objects.requireNonNullElse(mode, "list");

        List<String> addScript = new ArrayList<>();
        addScript.add("reservation/common");

        if(mode.equals("edit_reservation")) {
            pageTitle = "예약정보 수정";
        } else if (mode.equals("add_reservation")) {
            pageTitle = "예약 추가";
            addScript.add("reservation/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("addScript", addScript);

    }

}
