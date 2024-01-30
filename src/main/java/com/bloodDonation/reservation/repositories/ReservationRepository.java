package com.bloodDonation.reservation.repositories;

import com.bloodDonation.reservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long>,
        QuerydslPredicateExecutor<Reservation> {
    /*//편리한기능을 추가 - 이메일과 아이디로 조회할수있는 쿼리 작성
    @EntityGraph(attributePaths = "authorities")
    Optional<Reservation> findByEmail(String email);
    @EntityGraph(attributePaths = "authorities")
    Optional<Reservation> findByUserId(String userId);*/

    @Query("SELECT SUM(r.capacity) FROM Reservation r WHERE r.center.cCode = :cCode AND r.bookDateTime = :dateTime")
    Integer getTotalCapacity(@Param("cCode") Long cCode, @Param("dateTime") LocalDateTime dateTime);


}
