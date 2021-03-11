package org.tat.gginl.api.common;

import org.tat.gginl.api.domains.User;

public interface IUserProcessService {
	public void registerUser(User user);
	public User getLoginUser();
}
