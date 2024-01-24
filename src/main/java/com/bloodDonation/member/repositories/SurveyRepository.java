package com.bloodDonation.member.repositories;

import com.bloodDonation.mypage.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SurveyRepository extends JpaRepository<Survey, Long>, QuerydslPredicateExecutor<Survey> {
}
