package com.phonebook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "CONTACT_DETAILS")
public class Contact 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CONTACT_ID")
	private Integer contactId;
	@Column(name = "CONTACT_NAME")
	private String contactName;
	@Column(name = "CONTACT_EMAIL")
	private String contactEmail;
	@Column(name = "CONTACT_NUMBER")
	private Long contactNum;
	// For Soft Delete
	@Column(name = "ACTIVE_SWITCH")
	private String activeSw;
}
