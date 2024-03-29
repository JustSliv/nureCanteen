package org.nure.Repositories;

import org.nure.dto.BasketDto;
import org.nure.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BasketRepo extends JpaRepository<Basket, Long> {


   @Query(value = "SELECT basket.basket_id, product.product, product.name, product.category, product.description, product.price, basket.count FROM product, basket, usr WHERE usr.id = basket.id_user\n" +
      "AND basket.id_user = :user_id AND basket.id_product=product.product AND basket.active = true", nativeQuery = true)
   @Transactional
   List<BasketDto> getUserBasket(@Param("user_id") Long user_id);

   @Query(value = "SELECT * FROM basket ", nativeQuery = true)
   @Transactional
   List<Basket> selectAll();

   @Modifying
   @Query(value = "UPDATE basket SET active = false, check_id = :check_id WHERE id_user = :user_id AND check_id IS NULL", nativeQuery = true)
   @Transactional
   void changeActive(Long user_id, Long check_id);

}
