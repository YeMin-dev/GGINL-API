package org.tat.gginl.api.domains.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.gginl.api.common.IDGen;
import org.tat.gginl.api.domains.repository.IdGenRepository;

@Service
public class CustomIdGenerator implements ICustomIdGenerator {
	
	@Autowired
	private IdGenRepository idGenRepo;
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMM");
	@Override
	public String getNextId(String key, String branchId) {
		String id = null;
		try {
			id = formatId(idGenRepo.getNextId(key,branchId));
		} catch (PersistenceException p) {
			p.printStackTrace();
		}
		return id;
	}
	
	private String formatId(IDGen idGen) {
		String id = idGen.getMaxValue() + "";
		String prefix = idGen.getPrefix();
		String suffix = idGen.getSuffix();
		int maxLength = idGen.getLength();
		boolean isDateBased = idGen.isDateBased();
		int idLength = id.length();
		for (; (maxLength - idLength) > 0; idLength++) {
			id = '0' + id;
		}
		if (suffix == null) {
			suffix = "";
		}


			if (isDateBased) {

				id = prefix + "/" + getDateString() + "/" + id;
			} else {
				id = prefix + "/" + id;
			}
		return id;
	}

	private String getDateString() {
		return simpleDateFormat.format(new Date());
	}
	

	

}
