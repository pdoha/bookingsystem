package com.bloodDonation.file.entities;

import com.bloodDonation.commons.entities.BaseMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_fileInfo_gid", columnList = "gid"),
        @Index(name = "idx_fInfo_gid_loc", columnList = "gid,location")
})//많이 하는 조회는 인덱스 걸기
public class FileInfo extends BaseMember {

    @Id
    @GeneratedValue
    private Long seq; //파일 등록 번호, 서버에 업로드하는 파일명 기준

    @Column(length = 65, nullable = false)
    private String gid = UUID.randomUUID().toString();//게시글 하나에 여러개의 파일이 필요할시 groupid로 구별하기 위해서
    //문자열로 랜덤하게 유니크아이디를 만들수 있는 기능중 하나

    @Column (length=65)
    private String location;//그룹 안에서 위치별로 구분 ex)에디터, 첨부, 상세이미지등

    @Column (length = 80)
    private String fileName;

    @Column (length = 30)
    private String extension;//확장자(시퀀스 번호.확장자 하기 위해)

    @Column(length = 65)
    private String fileType;//extension이 없는 경우도 있기 때문에, 이미지만따로 뽑아오는

    //편의상 만든 종목
    @Transient
    private String filePath; //서버에 실제 올라간 경로

    @Transient
    private String fileUrl; //브라우저 주소창에 입력해서 접근할수 있는 경로(

    @Transient
    private List<String> thumbsPath; //썸네일 이미지 경로(삭제할 때)

    @Transient
    private List<String> thumbsUrl; //브라우저 주소창에 입력해서 접근할 수 있는 경로

    private boolean done;//글 작성 완료 -> 영구적 보관


}