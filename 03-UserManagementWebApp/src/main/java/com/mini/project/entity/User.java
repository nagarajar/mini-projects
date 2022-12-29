package com.mini.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user_dtls")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phno")
	private Long phno;
	
	@Column(name = "dob")
	private Date dob;
	
	@Column(name = "country_id")
	private Long countryId;
	
	@Column(name = "state_id")
	private Long stateId;
	
	@Column(name = "city_id")
	private Long cityId;
	
	@Column(name = "user_pwd")
	private String userPwd;
	
	@Column(name = "account_status",columnDefinition = "varchar(255) default 'locked'")
	private String accStatus;
	
}
