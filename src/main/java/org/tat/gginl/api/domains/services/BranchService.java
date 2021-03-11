package org.tat.gginl.api.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.repository.BranchRepository;
import org.tat.gginl.api.exception.DAOException;
import org.tat.gginl.api.exception.ErrorCode;

@Service
public class BranchService {

	@Autowired
	private BranchRepository repository;

	public List<Branch> findAll() {
		return repository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return repository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return repository.findAllColumnName();
	}

	@Transactional
	public Optional<Branch> findById(String id) throws DAOException {
		if (repository.findById(id).isPresent()) {
			return repository.findById(id);
		}
		else {
			throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in Branch");
		}
	}

}
