package org.tat.gginl.api.domains.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.gginl.api.domains.TownshipCode;
import org.tat.gginl.api.domains.repository.TownShipCodeRepository;
import org.tat.gginl.api.exception.DAOException;
import org.tat.gginl.api.exception.ErrorCode;

@Service
public class TownShipCodeService {

	@Autowired
	private TownShipCodeRepository repository;

	@Transactional
	public List<TownshipCode> findAll() {
		return repository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return repository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return repository.findAllColumnName();
	}

	@Transactional
	public Optional<TownshipCode> findByTownshipcodeno(String townshipcodeno, String stateCodeId) {
		if (townshipcodeno != null || stateCodeId != null) {
			if (repository.findByTownshipcodeno(townshipcodeno, stateCodeId).isPresent()) {
				return repository.findByTownshipcodeno(townshipcodeno, stateCodeId);
			}
			else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND,
						  " not found  TownShipCode ("+ townshipcodeno + ") in StateCode");
			}
		}
		else {
			return Optional.empty();
		}
	}

}
