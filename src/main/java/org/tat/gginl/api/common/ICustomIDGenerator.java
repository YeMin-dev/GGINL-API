package org.tat.gginl.api.common;

import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.User;

public interface ICustomIDGenerator {

	public IDGen updateIDGen(IDGen idGen) throws CustomIDGeneratorException;

	public String getNextIdForAutoRenewal(String key);

	public String getPrefixForAutoRenewal(Class cla);

	String getNextId(String key, String productCode, boolean isIgnoreBranch) throws CustomIDGeneratorException;

	String getNextId(String key, String productCode, Branch branch, boolean isIgnoreBranch) throws CustomIDGeneratorException;

	String getPrefix(Class cla, boolean isIgnoreBranch);

	String getPrefix(Class cla, User user, boolean isIgnoreBranch);

	IDGen getIDGen(String key, boolean isIgnoreBranch) throws CustomIDGeneratorException;

	String getNextIdWithBranchCode(String key, String productCode, Branch branch, boolean isIgnoreBranch) throws CustomIDGeneratorException;
}
