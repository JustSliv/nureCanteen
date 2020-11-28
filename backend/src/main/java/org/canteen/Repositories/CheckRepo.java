package org.canteen.Repositories;

import org.canteen.Repositories.dto.CheckDTO;
import org.canteen.models.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckRepo  extends JpaRepository<Check, Long> {

   @Query(value = "SELECT checks.check_id, usr.email, checks.canteen, checks.date, product.name, basket.count, product.price\n" +
      "FROM product, basket, usr, checks\n" +
      "WHERE usr.id = basket.id_user AND basket.id_user = :user_id\n" +
      "GROUP BY checks.check_id, usr.email, checks.canteen, checks.date, product.name, product.price, basket.count", nativeQuery = true)
   List<CheckDTO> getProductSumInCheck (@Param("user_id") Long user_id);
}
