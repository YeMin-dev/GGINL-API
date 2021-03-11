package org.tat.gginl.api.domains.services;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.domains.LifePolicy;
import org.tat.gginl.api.domains.repository.LifePolicyRepository;
import org.tat.gginl.api.exception.DAOException;
import org.tat.gginl.api.exception.ErrorCode;

@Service
public class LifepolicyService {
	
	@Autowired
	private LifePolicyRepository lifePolicyRepository;
	
	@Transactional
	public Optional<LifePolicy> findByPolicyNo(String policyNo) throws DAOException {
		if (!StringUtils.isBlank(policyNo)) {
			if (lifePolicyRepository.findByPolicyNo(policyNo).isPresent()) {
				return lifePolicyRepository.findByPolicyNo(policyNo);
			}
			else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND,policyNo + " not found in lifePolicy");
			}
		}
		else {
			return Optional.empty();
		}

	}
}
