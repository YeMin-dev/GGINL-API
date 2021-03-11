package org.tat.gginl.api.domains.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tat.gginl.api.domains.TownshipCode;

public interface TownShipCodeRepository  extends JpaRepository<TownshipCode,String>{
	@Query(value = "SELECT * FROM  TownShipCode",nativeQuery = true)
	List<Object[]> findAllNativeObject();
	
	@Query(value = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TownShipCode'",nativeQuery = true)
	List<Object> findAllColumnName();
	
	@Query("select t from TownshipCode  t where t.townshipcodeno =:townshipcodeno  and t.stateCode.id =:stateCodeid ")
	public Optional<TownshipCode> findByTownshipcodeno(@Param("townshipcodeno") String townshipcodeno ,@Param("stateCodeid") String stateCodeid);
}
