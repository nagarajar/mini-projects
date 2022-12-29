package com.mini.project.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.mini.project.dto.LoginForm;
import com.mini.project.dto.UnlockAccForm;
import com.mini.project.entity.City;
import com.mini.project.entity.Country;
import com.mini.project.entity.State;
import com.mini.project.entity.User;
import com.mini.project.repository.CityRepository;
import com.mini.project.repository.CountryRepository;
import com.mini.project.repository.StateRepository;
import com.mini.project.repository.UserRepository;
import com.mini.project.util.PasswordGeneratorUtil;

public class UserManagementServiceImpl implements IUserManagementService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CountryRepository countryRepo;
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private CityRepository cityRepo;

	@Override
	public String checkEmail(String email) {
		User findByEmail = userRepo.findByEmail(email);
		if (findByEmail != null) {
			return "This email is already in use...!";
		}
		return null;
	}

	@Override
	public Map<Long, String> getCountries() {
		List<Country> countries = countryRepo.findAll(Sort.by("countryName"));
		Map<Long, String> countryMap = new LinkedHashMap<>();
		for (Country country : countries) {
			countryMap.put(country.getCountryId(), country.getCountryName());
		}
		return countryMap;
	}

	@Override
	public Map<Long, String> getStates(Integer countryId) {
		List<State> states = stateRepo.getAllStatesBasedOnCountryId(countryId);
		Map<Long, String> stateMap = new LinkedHashMap<>();
		for (State state : states) {
			stateMap.put(state.getStateId(), state.getStateName());
		}
		return stateMap;
	}

	@Override
	public Map<Long, String> getCities(Integer stateId) {
		List<City> cities = cityRepo.getAllCitiesBasedOnStateId(stateId);
		Map<Long, String> cityMap = new LinkedHashMap<>();
		for (City city : cities) {
			cityMap.put(city.getCityId(), city.getCityName());
		}
		return cityMap;
	}

	@Override
	public String registerUser(User user) {
		String checkEmail = checkEmail(user.getEmail());

		if (checkEmail == null) {
			user = userRepo.save(user);
			if (user != null) {
				user.setUserPwd(PasswordGeneratorUtil.generateSecurePassword());
				return "User registered successfully";
			}
			return "User failed to register";
		}
		return "Failed to register because this email is already in use...!";
	}

	@Override
	public String unlockAccount(UnlockAccForm unlockAccForm) {
		User user = userRepo.findByEmail(unlockAccForm.getEmail());
		if (user != null) {
			if (user.getUserPwd().equals(unlockAccForm.getTempPwd())) {
				if (unlockAccForm.getNewPwd().equals(unlockAccForm.getConfirmNewPwd())) {
					user.setUserPwd(unlockAccForm.getConfirmNewPwd());
					user.setAccStatus("unlocked");
					return "‘Account unlocked, please proceed with login";
				} else {
					return "New and Confirm passwords are not matching";
				}

			} else {
				return "Please enter the valid temporary password";
			}
		} else {
			return "User Not Fond...!";
		}
	}

	@Override
	public String login(LoginForm loginForm) {
		User user = userRepo.findByEmail(loginForm.getEmail());
		if (user != null) {
			if (user.getUserPwd().equals(loginForm.getPassword())) {
				if (user.getAccStatus().equalsIgnoreCase("unlocked")) {
					return "Your logged in sucessfully";
				} else {
					return "Your Account Is Locked";
				}
			} else {
				return "Invalid Credentials";
			}
		} else {
			return "User Not Fond...!";
		}
	}

	@Override
	public String forgotPwd(String email) {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			return user.getUserPwd();
		}
		return "User Not Fond...!";

	}

}
