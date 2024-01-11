package com.bloodDonation.commons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
//부모 클래스는 테이블과 매핑하지 않고, 오로지 부모 클래스를 상속 받는 자식 클래스에게 부모 클래스가 가지는 칼럼만 매핑정보로 제공
@EntityListeners(AuditingEntityListener.class)
public abstract class Base {//자동 저장
    //공통으로 사용할 속성

    @CreatedDate
    @Column(updatable = false) //수정 불가능하게
    private LocalDateTime createdAt; //가입일자

    @LastModifiedDate
    @Column(insertable = false) //삽입만 가능하게
    private LocalDateTime modifiedAt; //수정일자
}
