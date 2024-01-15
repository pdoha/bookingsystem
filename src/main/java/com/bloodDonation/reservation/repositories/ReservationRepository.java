package com.bloodDonation.reservation.repositories;

import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.entities.QMember;
import com.bloodDonation.reservation.entities.QReservation;
import com.bloodDonation.reservation.entities.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>,
        QuerydslPredicateExecutor<Reservation> {
    //편리한기능을 추가 - 이메일과 아이디로 조회할수있는 쿼리 작성
    @EntityGraph(attributePaths = "authorities")
    Optional<Reservation> findByEmail(String email);
    @EntityGraph(attributePaths = "authorities")
    Optional<Reservation> findByUserId(String userId);


}
