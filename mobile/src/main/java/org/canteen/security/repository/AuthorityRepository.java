package org.canteen.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.canteen.security.model.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
