package com.bloodDonation.reservation.controllers;

import com.bloodDonation.admin.center.controllers.CenterSearch;
import lombok.Data;

import java.util.List;

@Data
public class RCenterSearch extends CenterSearch {
    private List<String> sopr;    // 운영 정보
}
