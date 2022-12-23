package com.mini.project.model;

//import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "citizen_plans_info")
public class CitizenPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer cid;
	
	@Column(name = "name")
	private String cname;
	
	@Column(name = "email")
	private String cemail;
	
	@Column(name = "phone_num")
	private Integer phno;
	
	@Column(name = "gender")
	private String gender;

	@Column(name = "ssn")
	private Integer ssn;
	
	@Column(name = "plan_name")
	private String planName;
	
	@Column(name = "plan_status")
	private String planStatus;
	
	/*
	 * @Column(name = "start_date") private Date startDate;
	 * 
	 * @Column(name = "end_date") private Date endDate;
	 */
}
