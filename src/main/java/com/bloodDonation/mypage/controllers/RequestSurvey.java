package com.bloodDonation.mypage.controllers;

import lombok.Data;

@Data
public class RequestSurvey {
 private String mode = "step1";
 private boolean[] questions1;
 private boolean[] questions2;
}
