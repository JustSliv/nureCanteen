package org.nure.Repositories;

import org.nure.dto.CheckDTO;
import org.nure.models.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CheckRepo  extends JpaRepository<Check, Long> {

   @Query(value = "SELECT checks.check_id, usr.email, checks.canteen, " +
      "MAX(checks.date) AS date, MAX(checks.time) AS time, product.name, basket.count, product.price" +
      " FROM product, basket, usr, checks" +
      " WHERE usr.id = basket.id_user" +
      " AND checks.time = (SELECT MAX(checks.time) FROM checks WHERE checks.id_user = :user_id)" +
      " AND checks.date = (SELECT MAX(checks.date) FROM checks WHERE checks.id_user = :user_id)" +
      " AND basket.id_product = product.product" +
      " AND basket.check_id = checks.check_id" +
      " AND basket.id_user = :user_id" +
      " GROUP BY checks.check_id, usr.email, checks.canteen, checks.date, product.name, product.price, basket.count", nativeQuery = true)
   List<CheckDTO> getProductSumInCheck (@Param("user_id") Long user_id);

   @Modifying
   @Query(value = "UPDATE checks SET date= CAST(:date as date) WHERE check_id=:check_id", nativeQuery = true)
   @Transactional
   void setDate(@Param("date") String date, @Param("check_id") Long id);

   @Modifying
   @Query(value = "UPDATE checks SET time=CAST(:time as time) WHERE check_id=:check_id", nativeQuery = true)
   @Transactional
   void setTime(@Param("time") String time, @Param("check_id") Long id);

}
