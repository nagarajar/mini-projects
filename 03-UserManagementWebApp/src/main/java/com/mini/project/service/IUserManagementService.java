package com.mini.project.service;

import java.util.Map;

import com.mini.project.dto.LoginForm;
import com.mini.project.dto.UnlockAccForm;
import com.mini.project.dto.UserForm;


public interface IUserManagementService {
	
	public String checkEmail(String email);
	
	public Map<Long, String> getCountries();

	public Map<Long, String> getStates(Long countryId);

	public Map<Long, String> getCities(Long stateId);

	public String registerUser(UserForm userForm);

	public String unlockAccount(UnlockAccForm unlockAccForm);

	public String login(LoginForm loginForm);

	public String forgotPwd(String email);
}
