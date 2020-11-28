package org.canteen.Repositories;

import org.canteen.Repositories.dto.CheckDTO;
import org.canteen.models.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckRepo  extends JpaRepository<Check, Long> {

   @Query(value = "SELECT checks.check_id, usr.email, checks.canteen, checks.date, product.name, product.count, SUM(product.price * basket.count) FROM product, basket, usr, checks WHERE usr.id = basket.id_user\n" +
      " AND basket.id_user = :user_id GROUP BY product.name, checks.check_id, usr.email", nativeQuery = true)
   List<CheckDTO> getProductSumInCheck (@Param("user_id") Long user_id);
}
