package com.bloodDonation.Survey.repository;

import com.bloodDonation.mypage.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SurveyRepository extends JpaRepository<Survey, Long>, QuerydslPredicateExecutor<Survey> {

    /*Optional<Survey> findById(Long seq);*/

    /*Optional<Survey> findBymemberUserNo(Long memberUserNo);
    default boolean existsById(Long seq){
        //Survey에 seq 존재하는지 체크
        QSurvey survey = QSurvey.survey;
        return exists(survey.seq.eq(seq));
    }*/
    /*
    default boolean existsByuserNo(Long userNo){
        //Survey에 userno 존재하는지 체크
        QSurvey survey = QSurvey.survey;
        return exists(survey.member.eq());
    }
    */
}
