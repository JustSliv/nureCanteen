package org.nure.Repositories;

import org.nure.models.Comment;
import org.nure.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

   @Query(value = "SELECT * FROM comment WHERE comment.id_product = :id", nativeQuery = true)
   List<Comment> findCommentsForProduct(@Param("id") Long id);
}
