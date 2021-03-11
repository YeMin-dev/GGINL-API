package org.tat.gginl.api.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.common.SecurityUser;

public interface UserRepository extends JpaRepository<SecurityUser, Integer>{
	
	 boolean existsByUsername(String username);

	  SecurityUser findByUsername(String username);

	  @Transactional
	  void deleteByUsername(String username);

}
