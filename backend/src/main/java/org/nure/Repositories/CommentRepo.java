package org.nure.Repositories;

import org.nure.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepo extends JpaRepository<Comment, Long> {



   @Modifying
   @Query(value = "INSERT INTO comment (date, msg, id_product, id_user) VALUES (:date, :msg, :id_product, :id_user)", nativeQuery = true)
   @Transactional
   void addComment(@Param("date") String date, @Param("msg") String msg, @Param("id_product") Long id_product, @Param("id_user") Long id_user);
}
