package com.clicktour.clicktour.repository;

import com.clicktour.clicktour.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b ORDER BY b.id DESC")
    List<Board> findAllDesc();

    @Modifying // 삽입, 수정, 삭제 쿼리 사용시 필요한 어노테이션
    @Query("update Board b set b.view = b.view + 1 where b.id = :id")
    int updateView(@Param("id") Long id);

    Optional<Board> findById(Long id);
}
