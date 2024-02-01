package com.bloodDonation.member.repositories;

import com.bloodDonation.member.entities.Member;

import com.bloodDonation.member.entities.QMember;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>,
        //추가할 쿼리가 많을때
        QuerydslPredicateExecutor<Member>{

    //편리한기능을 추가 - 이메일과 아이디로 조회할수있는 쿼리 작성
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByEmail(String email);
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByUserId(String userId);



    default boolean existsByEmail(String email){
        //이메일 존재하는지 체크
        QMember member = QMember.member;
        return exists(member.email.eq(email));
    }

    default boolean existsByUserId(String userId){
        //아이디 존재하는지 체크
        QMember member = QMember.member;
        return exists(member.userId.eq(userId));
    }

    /**
     * 이메일과 회원명으로 조회되는지 체크 (비밀번호찾기)
     * @param email
     * @param name
     * @return
     */
    default boolean existsByEmailAndName(String email, String name){
        QMember member = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(member.email.eq(email))
                .and(member.mName.eq(name));

        return exists(builder);
    }

}