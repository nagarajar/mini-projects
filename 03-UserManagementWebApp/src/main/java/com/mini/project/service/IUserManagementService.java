package com.mini.project.service;

import java.util.Map;

import com.mini.project.dto.LoginForm;
import com.mini.project.dto.UnlockAccForm;
import com.mini.project.entity.User;

public interface IUserManagementService {
	
	public String checkEmail(String email);
	
	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);

	public String registerUser(User user);

	public String unlockAccount(UnlockAccForm unlockAccForm);

	public String login(LoginForm loginForm);

	public String forgotPwd(String email);
}
