package com.bloodDonation.admin.member.controllers;

import com.bloodDonation.member.Authority;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RequestControl {
    @NotBlank
    private String userId;
    private boolean enable; //탈퇴여부
    private List<Authority> authorities; //권한은 여러개
}
