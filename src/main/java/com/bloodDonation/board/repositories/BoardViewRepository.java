package com.bloodDonation.board.repositories;


import com.bloodDonation.board.entities.BoardView;
import com.bloodDonation.board.entities.BoardViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardViewRepository extends JpaRepository<BoardView, BoardViewId>, QuerydslPredicateExecutor<BoardView> {
    //조회와 관현됨
}
