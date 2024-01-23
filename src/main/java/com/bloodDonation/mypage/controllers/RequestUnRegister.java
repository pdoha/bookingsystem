package com.bloodDonation.mypage.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestUnRegister {

    @NotBlank
    private String userPw;
}
