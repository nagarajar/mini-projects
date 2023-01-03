package com.mini.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mini.project.dto.LoginForm;
import com.mini.project.dto.UnlockAccForm;
import com.mini.project.dto.UserForm;
import com.mini.project.service.IUserManagementService;

@RestController
public class UserRestController {
	
	@Autowired
	private IUserManagementService userService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginForm loginForm){
		String status = userService.login(loginForm);
		return ResponseEntity.ok(status);
	}
	
	@GetMapping("/countries")
	public ResponseEntity<Map<Long, String>> loadCountries(){ 
		return ResponseEntity.ok(userService.getCountries());
	}
	
	@GetMapping("/states/{countryId}")
	public ResponseEntity<Map<Long, String>> loadstates(@PathVariable Integer countryId){ 
		return ResponseEntity.ok(userService.getStates(countryId));
	}
	
	@GetMapping("/cities/{stateId}")
	public ResponseEntity<Map<Long, String>> loadCities(@PathVariable Integer stateId){ 
		return ResponseEntity.ok(userService.getCities(stateId));
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<String> emailCheck(@PathVariable String email){ 
		return ResponseEntity.ok(userService.checkEmail(email));
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> userRegistration(@RequestBody UserForm userForm){
		String status = userService.registerUser(userForm);
		return new ResponseEntity<>(status, HttpStatus.CREATED);
	}
	
	@PostMapping("/unlock")
	public ResponseEntity<String> unlockAccount(@RequestBody UnlockAccForm unlockAccForm){
		String status = userService.unlockAccount(unlockAccForm);
		return ResponseEntity.ok(status);
	}
	
	@GetMapping("/forgotpwd/{email}")
	public ResponseEntity<String> forgotPwd(@PathVariable String email){ 
		String status = userService.forgotPwd(email);
		return ResponseEntity.ok(status);
	}

}
