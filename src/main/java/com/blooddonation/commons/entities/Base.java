package com.blooddonation.commons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Base {

    @CreatedDate
    @Column(updatable = false) //수정 불가능하게
    private LocalDateTime createdAt; //가입일자

    @LastModifiedDate
    @Column(insertable = false) //수정만 가능하게
    private LocalDateTime modifiedAt; //수정일자
}
