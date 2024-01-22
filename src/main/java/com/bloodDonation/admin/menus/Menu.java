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
        //menus : 주메뉴 (회원관리)
        //MenuDetail : 부메뉴 ( list로 받아옴!)
        menus.put("member", Arrays.asList(


                new MenuDetail("list", "회원 목록", "/admin/member"),
                new MenuDetail("add", "회원 등록", "/admin/member/add")


        ));
        //게시판 메뉴코드
        menus.put("board", Arrays.asList(
                new MenuDetail("list","게시판 목록", "/admin/board"),
                new MenuDetail("add", "게시판 등록", "/admin/board/add"),
                new MenuDetail("posts","게시글 관리", "/admin/board/posts")
        ));

        // 센터 메뉴코드
        menus.put("center", Arrays.asList(
                new MenuDetail("list", "센터 목록", "/admin/center"),
                new MenuDetail("add_center", "센터 등록", "/admin/center/add_center")
        ));
        //예약현황 메뉴코드
        menus.put("reservation", Arrays.asList(
                new MenuDetail("list", "예약자 목록", "/admin/reservation"),
                new MenuDetail("add", "예약자 추가", "/admin/reservation/add")

        ));
    }

    //주메뉴 코드를 갖고 서브메뉴를 조회할 수 있게
    //매개변수 -  code = 주메뉴 코드
    public static List<MenuDetail> getMenus(String code) {
        return menus.get(code);
    }

}
