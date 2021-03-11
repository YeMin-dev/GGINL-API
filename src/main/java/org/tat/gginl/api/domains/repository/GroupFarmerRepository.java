package org.tat.gginl.api.domains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.gginl.api.domains.GroupFarmerProposal;

public interface GroupFarmerRepository extends JpaRepository<GroupFarmerProposal, String>{

}
