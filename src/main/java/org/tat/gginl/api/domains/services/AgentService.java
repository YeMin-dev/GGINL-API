package org.tat.gginl.api.domains.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.domains.Agent;
import org.tat.gginl.api.domains.repository.AgentRepository;
import org.tat.gginl.api.exception.DAOException;
import org.tat.gginl.api.exception.ErrorCode;

@Service
public class AgentService {

	@Autowired
	private AgentRepository repository;

	public List<Agent> findAll() {
		return repository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return repository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return repository.findAllColumnName();
	}

	@Transactional
	public Optional<Agent> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (repository.findById(id).isPresent()) {
				return repository.findById(id);
			}
			else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in Agent");
			}
		}
		else {
			return Optional.empty();
		}

	}

}
