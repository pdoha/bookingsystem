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
    }

    public static List<MenuDetail> getMenus(String code) {
        return menus.get(code);
    }

}
