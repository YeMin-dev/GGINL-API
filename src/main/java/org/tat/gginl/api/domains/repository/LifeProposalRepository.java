package org.tat.gginl.api.domains.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.domains.LifeProposal;

public interface LifeProposalRepository extends JpaRepository<LifeProposal, String> {

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "INSERT INTO WORKFLOW_HIST VALUES (?1,?2,?3,?4,'INUSR001000009661731032019','INUSR001000009661731032019',NULL,?6,'INUSR001000009661731032019',?5,NULL,NULL, 1,0)")
	public void saveToWorkflowHistory(String id, String referenceNo, String referenceType, String workflowTask,
			String createdDate, String workflowDate);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "INSERT INTO GGINLAPP (ID, APPNAME, CREATEDDATE, VERSION) VALUES (?1, ?2, ?3, 1)")
	public void saveToGginlApp(String id, String appName, Date createdDate);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "SELECT * FROM GGINLAPP WHERE ID = ?1")
	public List<Object> searchByIdInGginlApp(String id);
	
}
