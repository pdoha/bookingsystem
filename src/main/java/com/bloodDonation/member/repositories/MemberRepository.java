package com.bloodDonation.member.repositories;

import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.entities.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>,
    //추가할 쿼리가 많을때
    QuerydslPredicateExecutor<Member>{

    //편리한기능을 추가 - 이메일과 아이디로 조회할수있는 쿼리 작성
    Optional<Member> findByEmail(String email);
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





    }

