package com.blooddonation.file.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FileInfo {
    @Id @GeneratedValue
    private Long seq; //파일 등록번호, 서버에 업로드하는 파일명 기준

    //랜덤하게 유니크아이디를 만들 수 있는 편의기능(문자열반환)
    @Column(length=65, nullable=false)
    private String gid = UUID.randomUUID().toString();

    @Column(length=65)
    private String location;

    @Column(length=100)
    private String fileName;

    @Column(length=30)
    private String extension;

    private boolean done;
}
