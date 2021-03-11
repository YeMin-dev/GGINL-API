package org.tat.gginl.api.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tat.gginl.api.domains.ShortEndowmentExtraValue;

@Repository
public interface ShortEndowmentExtraValueRepository extends JpaRepository<ShortEndowmentExtraValue, String> {
	
	@Query("SELECT se FROM  ShortEndowmentExtraValue se WHERE se.referenceNo = :referenceNo")
	public ShortEndowmentExtraValue findShortEndowmentExtraValue(@Param("referenceNo") String referenceNo);
}
