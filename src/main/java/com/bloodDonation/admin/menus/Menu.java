package com.bloodDonation.admin.menus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//부메뉴 설정!!!!!!(가로메뉴)
public class Menu {
    private final static Map<String, List<MenuDetail>> menus;

    static {
        menus = new HashMap<>();
        //회원 메뉴코드
        menus.put("member", Arrays.asList(
           new MenuDetail("list", "회원 목록", "/admin/member"),
           new MenuDetail("authority", "회원 권한","/admin/member/authority")
        ));
        //게시판 메뉴코드
        menus.put("board", Arrays.asList(
                new MenuDetail("list","게시판 목록", "/admin/board"),
                new MenuDetail("add", "게시판 등록", "/admin/bard/add"),
                new MenuDetail("posts","게시글 관리", "/admin/board.posts")
        ));
        // 예약 메뉴코드
        menus.put("reservation", Arrays.asList(
                new MenuDetail("list","예약현황", "admin/center"),
                new MenuDetail("center","지점 목록","admin/center/center"),
                new MenuDetail("add_branch","지점 등록","admin/center/add_branch"),
                new MenuDetail("holiday", "휴무일 관리", "admin/center/holiday")
        ));
    }

    public static List<MenuDetail> getMenus(String code) {
        return menus.get(code);
    }

}
