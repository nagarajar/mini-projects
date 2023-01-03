package com.mini.project.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.mini.project.dto.LoginForm;
import com.mini.project.dto.UnlockAccForm;
import com.mini.project.dto.UserForm;
import com.mini.project.entity.City;
import com.mini.project.entity.Country;
import com.mini.project.entity.State;
import com.mini.project.entity.User;
import com.mini.project.repository.CityRepository;
import com.mini.project.repository.CountryRepository;
import com.mini.project.repository.StateRepository;
import com.mini.project.repository.UserRepository;
import com.mini.project.util.MailSenderUtil;
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
	@Autowired
	private MailSenderUtil sendMail;

	@Override
	public String checkEmail(String email) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			return "UNIQUE";
		}
		return "DUPLICATE";
	}

	@Override
	public Map<Long, String> getCountries() {
		List<Country> countries = countryRepo.findAll(Sort.by("countryName"));
		Map<Long, String> countryMap = new LinkedHashMap<>();
		countries.forEach(country -> countryMap.put(country.getCountryId(), country.getCountryName()));
		return countryMap;
	}

	@Override
	public Map<Long, String> getStates(Integer countryId) {
		List<State> states = stateRepo.getAllStatesBasedOnCountryId(countryId);
		Map<Long, String> stateMap = new LinkedHashMap<>();
		states.forEach(state -> stateMap.put(state.getStateId(), state.getStateName()));
		return stateMap;
	}

	@Override
	public Map<Long, String> getCities(Integer stateId) {
		List<City> cities = cityRepo.getAllCitiesBasedOnStateId(stateId);
		Map<Long, String> cityMap = new LinkedHashMap<>();
		cities.forEach(city -> cityMap.put(city.getCityId(), city.getCityName()));
		return cityMap;
	}

	@Override
	public String registerUser(UserForm userForm) {
		String checkEmail = checkEmail(userForm.getEmail());

		if (checkEmail.equalsIgnoreCase("UNIQUE")) {
			// 1. Copy the data from binding obj to entity obj
			User user = new User();
			BeanUtils.copyProperties(userForm, user);
			// 2. Generate and set random password
			// user.setUserPwd(PasswordGeneratorUtil.generateSecurePassword());
			user.setUserPwd(generateRandomPwd());
			// 3. Set Account as LOCKED
			user.setAccStatus("LOCKED");
			// 4. Save the entity
			user = userRepo.save(user);
			// 5. Send email to unlock account
			String status = sendMail.sendUnlockMail(user);
			return status;
		}
		return "User failed to register because this email is already in use...!";
	}

	private String generateRandomPwd() {
		String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int pwdLength = 8;
		for (int i = 1; i <= 8; i++) {
			int index = random.nextInt(pwdLength);
			sb.append(text.charAt(index));
		}

		return sb.toString();
	}

	@Override
	public String unlockAccount(UnlockAccForm unlockAccForm) {
		// 1. Get user details based on email
		User user = userRepo.findByEmail(unlockAccForm.getEmail());

		// 2. Validate the tempPwd and reset to new pwd
		if (user != null && user.getUserPwd().equals(unlockAccForm.getTempPwd())) {
			user.setUserPwd(unlockAccForm.getNewPwd());
			// Change the accSatus
			user.setAccStatus("UNLOCKED");
			// Save user
			user = userRepo.save(user);
			return "‘Account unlocked, please proceed with login";
		}
		return "Please enter the valid temporary password";
	}

	@Override
	public String login(LoginForm loginForm) {
		// 1. Get user details based on email and password
		User user = userRepo.findByEmailAndUserPwd(loginForm.getEmail(), loginForm.getPassword());

		// 2. If user obj is null
		if (user == null) {
			return "Invalid Credentials";
		}

		// 3. If Account is locked
		if (user.getAccStatus().equals("LOCKED")) {
			return "Your Account Is Locked";
		}

		// 4. validation of email and password is true and status is unlocked
		return "Your logged in sucessfully";

	}

	@Override
	public String forgotPwd(String email) {
		// 1. Get user details based on email
		User user = userRepo.findByEmail(email);
		// 2. If user obj is null
		if (user == null) {
			return "No Account Fond...!";
		}
		// 3. If user found
		String status = sendMail.sendForgotPwdMail(user);
		return status;

	}

}
