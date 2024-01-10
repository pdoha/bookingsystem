package org.choongang.admin.config.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class Configs {
//설정 코드별로 유연하게 하기 위해서
    @Id
    @Column(length = 60)
    private String code; //설정 코드

    @Lob
    private  String data;//설정 데이터

}
