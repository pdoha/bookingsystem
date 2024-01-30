package com.bloodDonation.admin.member.controllers;

import com.bloodDonation.member.Authority;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
@Data
public class RequestUpdate {
    //회원 수정쪽 일부 데이터만 검증 추가
    private String mode = "edit";
    @NotBlank //필수항목
    private String userId;
    private List<Authority> authorities;

    @NotBlank @Email
    private String email;
    private String userPw;
    private String confirmPw;
}
