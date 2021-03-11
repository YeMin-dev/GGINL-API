package org.tat.gginl.api.domains.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tat.gginl.api.domains.SalePoint;

public interface SalePointRepository extends JpaRepository<SalePoint, String>{
	
	@Query(value = "SELECT * FROM  SalePoint",nativeQuery = true)
	List<Object[]> findAllNativeObject();
	
	@Query(value = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'SalePoint'",nativeQuery = true)
	List<Object> findAllColumnName();


}
