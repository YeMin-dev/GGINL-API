package org.tat.gginl.api.domains;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalPolicyRepository extends JpaRepository<MedicalPolicy, String> {

  public Optional<MedicalPolicy> findByPolicyNo(String policyNo);
}
