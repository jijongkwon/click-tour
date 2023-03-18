package com.clicktour.clicktour.repository;

import com.clicktour.clicktour.domain.board.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments,Long> {
}
