package com.bloodDonation.area;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/area")
public class ApiAreaController {
    /**
     * 시구군 조회
     * @param sido
     * @return
     */
    @GetMapping("/sigugun/{sido}")
    public String[] sigugun(@PathVariable("sido") String sido) {
        return Areas.getSigugun(sido);
    }

    @GetMapping("/sido")
    public String[] sido() {
        return Areas.sido;
    }
}
