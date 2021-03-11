  package org.tat.gginl.api.domains.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tat.gginl.api.domains.Country;

public interface CountryRepository extends JpaRepository<Country,String> {
	
	@Query(value = "SELECT * FROM COUNTRY",nativeQuery = true)
	List<Object[]> findAllNativeObject();
	
	@Query(value = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'COUNTRY'",nativeQuery = true)
	List<Object> findAllColumnName();

}
