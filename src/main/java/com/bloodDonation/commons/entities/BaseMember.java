package com.bloodDonation.commons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMember extends Base{

    @CreatedBy
    @Column(length = 40, updatable = false)//업데이트 불가능
    private String createdBy; //최초 가입 날

    @LastModifiedBy
    @Column(length = 40, insertable = false)//삽입 불가능
    private String modifiedBy; //가입 변경

}
