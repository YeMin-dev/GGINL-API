package org.tat.gginl.api.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.GginlApp;

public interface GginlAppRepository extends JpaRepository<GginlApp, String> {
	
	

}
