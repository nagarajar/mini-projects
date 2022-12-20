package com.mini.project.model;

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
@Table(name = "employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer empId;
	
	@Column(name = "name")
	private String empName;
	
	@Column(name = "email")
	private String empEmail;
	
	@Column(name = "phone_num")
	private Integer emplPhoneNum;
	
	@Column(name = "gender")
	private String empGen;

	@Column(name = "ssn")
	private Integer empSsn;
	
	@Column(name = "plan_name")
	private String empPlanName;
	
	@Column(name = "plan_status")
	private String empPlanStatus;
	
	@Column(name = "start_date")
	private Date empStartDate;
	
	@Column(name = "end_date")
	private Date empEndDate;
}
