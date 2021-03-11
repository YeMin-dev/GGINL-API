package org.tat.gginl.api.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.domains.LifeProposal;

public interface LifeProposalRepository extends JpaRepository<LifeProposal, String> {

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "INSERT INTO WORKFLOW_HIST VALUES (?1,?2,?3,?4,'INUSR001000009661731032019','INUSR001000009661731032019',NULL,?6,'INUSR001000009661731032019',?5,NULL,NULL, 1,0)")
	public void saveToWorkflowHistory(String id, String referenceNo, String referenceType, String workflowTask, String createdDate, String workflowDate);

}
