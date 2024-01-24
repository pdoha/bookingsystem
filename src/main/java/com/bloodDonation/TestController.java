package com.bloodDonation;

import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController implements ExceptionProcessor {

    private final Utils utils;

    @GetMapping("/popup")
    public String popupTest(){
        return utils.tpl("test/popup");
    }
}
