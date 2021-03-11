package org.tat.gginl.api.domains.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.domains.StateCode;
import org.tat.gginl.api.domains.repository.StateCodeRepository;
import org.tat.gginl.api.exception.DAOException;
import org.tat.gginl.api.exception.ErrorCode;

@Service
public class StateCodeService {

	@Autowired
	private StateCodeRepository stateCodeRepository;

	@Transactional
	public Optional<StateCode> finById(String id) throws DAOException {
		if (id != null) {
			if (stateCodeRepository.findById(id).isPresent()) {
				return stateCodeRepository.findById(id);
			}
			else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in StateCode");
			}

		}
		else {
			return Optional.empty();
		}

	}

	@Transactional
	public Optional<StateCode> findByCodeNo(String statCode) {
		if (statCode != null) {
			if (stateCodeRepository.findByCodeNo(statCode).isPresent()) {

				return stateCodeRepository.findByCodeNo(statCode);
			}
			else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND,"State Code" + statCode + " not found in StateCode");
			}
		}
		else {
			return Optional.empty();
		}
	}

}
