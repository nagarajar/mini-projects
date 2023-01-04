package com.mini.project.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
import com.mini.project.util.EmailUtils;

@Service
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
	private EmailUtils emailUtils;

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
	public Map<Long, String> getStates(Long countryId) {
		List<State> states = stateRepo.getAllStatesBasedOnCountryId(countryId);
		Map<Long, String> stateMap = new LinkedHashMap<>();
		states.forEach(state -> stateMap.put(state.getStateId(), state.getStateName()));
		return stateMap;
	}

	@Override
	public Map<Long, String> getCities(Long stateId) {
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
			String to = userForm.getEmail();
			String subject = "Unlock IES Account";
			String body = readEmailBody("UNLOCK_ACC_EMAIL_BODY.txt", user);
			emailUtils.sendMail(to, subject, body);
			return "User Account Created";
		}
		return "User failed to register because this email is already in use...!";
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
		String subject = "Recovery Password";
		String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt", user);
		emailUtils.sendMail(email, subject, body);
		return "Password sent to registered email";

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
	
	private String readEmailBody(String filename, User user) {
		//2. Take a StringBuffer to store the file content bcs it is not Immutable
		StringBuffer sb = new StringBuffer();
		
		//1.Files.lines is used to read all lines at a time introduced in 1.8v
		try(Stream<String> lines = Files.lines(Paths.get(filename))) {
			//Replace all the Dynamic variables based on user
			lines.forEach(line ->{
				line = line.replace("${FNAME}", user.getFirstName());
				line = line.replace("${LNAME}", user.getLastName());
				line = line.replace("${TEMP_PWD}", user.getUserPwd());
				line = line.replace("${EMAL}", user.getEmail());
				line = line.replace("${PWD}", user.getUserPwd());
				
				sb.append(line);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Return as string
		return sb.toString();
	}

}
