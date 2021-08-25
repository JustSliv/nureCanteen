package org.nure.security.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.nure.security.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByUsername(String username);

   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);
   User findByUsername(String username);

   @Modifying
   @Query("UPDATE User u SET u.phone = :phone WHERE u.id = :id")
   void updatePhone(@Param("id") Long id, @Param("phone") String phone);

   @Query(value = "SELECT COUNT(*) FROM authority", nativeQuery = true)
   int getAuthCount();

   @Modifying
   @Transactional
   @Query(value = "INSERT INTO authority (name) VALUES ('ROLE_USER')", nativeQuery = true)
   void setAuth();


}
