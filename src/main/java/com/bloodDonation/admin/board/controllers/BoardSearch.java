package com.bloodDonation.admin.board.controllers;

import lombok.Data;
import java.util.List;

@Data
public class BoardSearch {
    private int page = 1; //첫 페이지
    private int limit = 20; //마지막 페이지

    private String bid; //게시판 아이디
    private List<String> bids; //게시판 아이디 목록

    private String bName; //게시판 이름
    private boolean active; //게시판 사용여부

    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드
}
