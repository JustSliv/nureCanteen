package org.canteen.Repositories;

import org.canteen.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

   @Query(value = "SELECT * FROM comment WHERE comment.id_product = :id", nativeQuery = true)
   List<Comment> findCommentsForProduct(@Param("id") Long id);
}
