package com.bloodDonation.center.repositories;

import com.bloodDonation.center.entities.CenterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//DB 접근 관련 코드
@Repository
public interface CenterInfoRepository extends JpaRepository<CenterInfo, Long>, QuerydslPredicateExecutor<CenterInfo> {
    //List<CenterInfo> findByCode(String cCode);
    Optional<CenterInfo> findBycCode(Long cCode);

}
