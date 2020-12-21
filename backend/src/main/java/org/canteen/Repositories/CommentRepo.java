package org.canteen.Repositories;

import org.canteen.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

   @Query(value = "SELECT * FROM comment WHERE comment.id_product = :id", nativeQuery = true)
   List<Comment> findCommentsForProduct(@Param("id") Long id);

   @Modifying
   @Query(value = "INSERT INTO comment (date, msg, id_product, id_user) VALUES (:date, :msg, :id_product, :id_user)", nativeQuery = true)
   @Transactional
   void addComment(@Param("date") String date, @Param("msg") String msg, @Param("id_product") Long id_product, @Param("id_user") Long id_user);
}
