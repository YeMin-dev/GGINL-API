package org.tat.gginl.api.domains.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.domains.SaleMan;
import org.tat.gginl.api.domains.repository.SaleManRepository;
import org.tat.gginl.api.exception.DAOException;
import org.tat.gginl.api.exception.ErrorCode;

@Service
public class SaleManService {

	@Autowired
	private SaleManRepository saleManRepository;

	public List<SaleMan> findAll() {
		return saleManRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return saleManRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return saleManRepository.findAllColumnName();
	}

	@Transactional
	public Optional<SaleMan> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (saleManRepository.findById(id).isPresent()) {
				return saleManRepository.findById(id);
			}
			else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in SaleMan");
			}
		}
		else {
			return Optional.empty();
		}

	}

}
