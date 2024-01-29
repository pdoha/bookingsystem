package com.bloodDonation.board.repositories;


import com.bloodDonation.board.entities.BoardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardDataRepository extends JpaRepository<BoardData, Long>, QuerydslPredicateExecutor<BoardData> {
    /**
     * 24.01.18
     * 아이디 중복 체크
     * @param userId
     * @return
     */
    @Query("SELECT DISTINCT b.board.bid FROM BoardData b WHERE b.member.userId=:userId")
    List<String> getUserBoards(@Param("userId") String userId);

    /**
     * 24.01.24
     * 답글 리스트
     * @param seq
     * @return
     */
    @Query("SELECT MIN(b.listOrder) FROM BoardData b WHERE b.parentSeq=:parentSeq")
    Long getLastReplyListOrder(@Param("parentSeq") Long seq);
}
