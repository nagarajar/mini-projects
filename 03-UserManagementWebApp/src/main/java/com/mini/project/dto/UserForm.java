package com.mini.project.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserForm {
	private String firstName;
	private String lastName;
	private String email;
	private Long phno;
	private Date dob;
	private String gender;
	private Long countryId;
	private Long stateId;
	private Long cityId;

}
