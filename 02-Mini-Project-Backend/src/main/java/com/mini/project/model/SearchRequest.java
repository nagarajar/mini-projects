package com.mini.project.model;

import java.util.Date;

import lombok.Data;

@Data
public class SearchRequest {
	private String planName;
	private String planStatus;
	private Date startDate;
	private Date endDate;
}
