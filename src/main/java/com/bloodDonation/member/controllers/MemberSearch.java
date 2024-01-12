package com.bloodDonation.member.controllers;

import lombok.Data;

@Data
public class MemberSearch {
    private int page = 1;
    private int limit = 20;
}
