package com.bloodDonation.member.repositories;

import com.bloodDonation.mypage.entities.QSurvey;
import com.bloodDonation.mypage.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long>, QuerydslPredicateExecutor<Survey> {

    Optional<Survey> findById(Long seq);

    default boolean existsById(Long seq){
        //Survey에 seq 존재하는지 체크
        QSurvey survey = QSurvey.survey;
        return exists(survey.seq.eq(seq));
    }

}
