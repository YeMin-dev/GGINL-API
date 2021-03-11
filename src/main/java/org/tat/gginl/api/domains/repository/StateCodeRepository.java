package org.tat.gginl.api.domains.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tat.gginl.api.domains.StateCode;

@Repository
public interface StateCodeRepository extends JpaRepository<StateCode,String> {
	
	public  Optional<StateCode> findByCodeNo(String codeNo);
	
	
}
