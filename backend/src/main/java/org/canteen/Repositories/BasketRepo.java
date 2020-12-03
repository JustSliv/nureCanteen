package org.canteen.Repositories;

import org.canteen.Repositories.dto.BasketDtoRepo;
import org.canteen.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BasketRepo extends JpaRepository<Basket, Long> {


   @Query(value = "SELECT basket.basket_id, product.product_id, product.name, product.category, product.description, product.price, basket.count FROM product, basket, usr WHERE usr.id = basket.id_user\n" +
      "AND basket.id_user = :user_id AND basket.id_product=product.product_id", nativeQuery = true)
   @Transactional
   List<BasketDtoRepo> getUserBasket(@Param("user_id") Long user_id);

   @Query(value = "DELETE FROM basket WHERE basket.id_user = :user_id", nativeQuery = true)
   @Transactional
   Basket deleteByUserUserId(Long user_id);
}
