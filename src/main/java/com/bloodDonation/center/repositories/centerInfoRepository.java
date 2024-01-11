package com.bloodDonation.center.repositories;

import com.bloodDonation.center.entities.centerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface centerInfoRepository extends JpaRepository<centerInfo, Long>, QuerydslPredicateExecutor<centerInfo> {
    //DB 접근 관련 코드

}
