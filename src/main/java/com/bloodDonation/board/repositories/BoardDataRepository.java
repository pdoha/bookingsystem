package com.bloodDonation.board.repositories;


import com.bloodDonation.board.entities.BoardData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardDataRepository extends JpaRepository<BoardData, Long> {
}
