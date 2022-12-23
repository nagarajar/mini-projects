package com.mini.project.service;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.DocumentException;
import com.mini.project.model.CitizenPlan;
import com.mini.project.model.SearchRequest;

public interface ICitizenPlanService {
	String save(CitizenPlan employee);
	List<CitizenPlan> getAllCitizenPlans(SearchRequest request);
	CitizenPlan getOneCitizenPlan(Integer empId);
	String update(CitizenPlan citizen); 
	String deleteCitizenPlan(Integer empId);
	
	List<String> getPlanNames();
	List<String> getPlanStatuses();
	void exportExcel(HttpServletResponse response) throws Exception;
	void exportPdf(HttpServletResponse response) throws DocumentException, IOException;
	
}
