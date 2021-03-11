package org.tat.gginl.api.domains.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.domains.Occupation;
import org.tat.gginl.api.domains.repository.OccupationRepository;
import org.tat.gginl.api.exception.DAOException;
import org.tat.gginl.api.exception.ErrorCode;

@Service
public class OccupationService {

	@Autowired
	private OccupationRepository occupationRepository;

	public List<Occupation> findAll() {
		return occupationRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return occupationRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return occupationRepository.findAllColumnName();
	}

	@Transactional
	public Optional<Occupation> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (occupationRepository.findById(id).isPresent()) {
				return occupationRepository.findById(id);
			}
			else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in Occupation");
			}
		}
		else {
			return Optional.empty();
		}

	}

}
