package com.clicktour.clicktour.repository;

import com.clicktour.clicktour.domain.board.Board;
import com.clicktour.clicktour.domain.board.dto.BoardResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b ORDER BY b.id DESC")
    List<Board> findAllDesc();
}
