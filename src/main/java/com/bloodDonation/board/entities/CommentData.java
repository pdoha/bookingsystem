package com.bloodDonation.board.entities;

import com.bloodDonation.commons.entities.Base;
import com.bloodDonation.member.entities.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 24.01.24
 * 댓글 관련 엔티티
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name="idx_comment_basic", columnList = "listOrder DESC, createdAt ASC"))
public class CommentData extends Base implements AuthCheck {
    @Id @GeneratedValue
    private Long seq;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="boardDataSeq")
    private BoardData boardData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberSeq")
    private Member member;

    @Column(length=40, nullable = false)
    private String commenter; // 작성자

    @Column(length=65)
    private String guestPw; // 비회원 댓글 수정, 삭제 비밀번호

    @Lob
    @Column(nullable = false)
    private String content; // 댓글 내용

    @Column(length=20)
    private String ip; // 작성자 IP 주소

    @Column(length=150)
    private String ua; // 작성자 User-Agent 정보

    private long listOrder; // 댓글 1차 정렬 기준
    private int depth; // 대댓글 들여쓰기 정도

    @Transient
    private boolean editable; //  수정가능여부

    @Transient
    private boolean deletable; // 삭제 가능여부

    @Transient
    private boolean mine; //소유자

    @Transient
    private boolean showEditButton;// 수정버튼 노출

    @Transient
    private boolean showDeleteButton; //수정버튼 노출
}
