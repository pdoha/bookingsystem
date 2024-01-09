package com.blooddonation.member.service;

import com.blooddonation.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

//회원이 없으면 회원이 없다는 메세지 예외 던져주기
//이걸 통하면 응답코드도 통제가능 commonException 상속받아서
public class MemberNotFoundException extends CommonException {


    //→ 기본생성자만 정의해서 메서드는 고정해서 넣어주자
    //(메세지는 고정되고, 응답코드도 고정된다)

    public MemberNotFoundException(){
        super("등록된 회원이 아닙니다.", HttpStatus.NOT_FOUND);
    }


}
