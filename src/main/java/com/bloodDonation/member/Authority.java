package com.bloodDonation.member;

import com.bloodDonation.commons.Utils;

public enum Authority {
    //각 상수마다 문구를 넣어주자
    ALL(Utils.getMessage("Authority.ALL", "commons")), // 전체(비회원 + 회원 + 관리자)
    USER(Utils.getMessage("Authority.USER", "commons")), // 일반 사용자
    MANAGER(Utils.getMessage("Authority.MANAGER", "commons")), // 부 관리자
    ADMIN(Utils.getMessage("Authority.ADMIN", "commons")); // 최고 관리자

    private final String title;

    Authority(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
