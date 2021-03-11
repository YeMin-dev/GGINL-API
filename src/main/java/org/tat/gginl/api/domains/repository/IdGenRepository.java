package org.tat.gginl.api.domains.repository;

import org.tat.gginl.api.common.IDGen;

public interface IdGenRepository{
	public IDGen getNextId(String generatedItem,String branchID);
	
}
