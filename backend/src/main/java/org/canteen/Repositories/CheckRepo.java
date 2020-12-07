package org.canteen.Repositories;

import org.canteen.Repositories.dto.CheckDTO;
import org.canteen.models.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CheckRepo  extends JpaRepository<Check, Long> {

   @Query(value = "SELECT checks.check_id, usr.email, checks.canteen, MAX(checks.date), product.name, basket.count, product.price\n" +
      "      FROM product, basket, usr, checks" +
      "      WHERE usr.id = basket.id_user " +
      "  AND basket.id_product = product.product_id" +
      " AND basket.check_id = checks.check_id" +
      "  AND basket.id_user = :user_id" +
      "      GROUP BY checks.check_id, usr.email, checks.canteen, checks.date, product.name, product.price, basket.count", nativeQuery = true)
   List<CheckDTO> getProductSumInCheck (@Param("user_id") Long user_id);

   @Modifying
   @Query(value = "UPDATE checks SET checks.date=:date", nativeQuery = true)
   @Transactional
   String setDate(@Param("date") String date);

}
