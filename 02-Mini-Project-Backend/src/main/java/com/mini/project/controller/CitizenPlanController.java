package com.mini.project.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.mini.project.model.CitizenPlan;
import com.mini.project.model.SearchRequest;
import com.mini.project.service.ICitizenPlanService;


@RestController
@RequestMapping("/CitizenPlan")
public class CitizenPlanController 
{
	@Autowired
	private ICitizenPlanService citizenPlanService;
	
	@PostMapping("/create")
	public ResponseEntity<String> createCitizenPlan(@RequestBody CitizenPlan citizenPlan){
		String msg = citizenPlanService.save(citizenPlan);
		return new ResponseEntity<String>(msg,HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CitizenPlan>> getCitizenPlans(@RequestBody SearchRequest request){
		List<CitizenPlan> citizenPlans = citizenPlanService.getAllCitizenPlans(request);
		return new ResponseEntity<List<CitizenPlan>>(citizenPlans,HttpStatus.OK);
	}
	
	@GetMapping("/{cid}")
	public ResponseEntity<?> getOneCitizenPlan(@PathVariable Integer cid){
		CitizenPlan citizenPlan = citizenPlanService.getOneCitizenPlan(cid);
		if(citizenPlan != null) {
			return new ResponseEntity<CitizenPlan>(citizenPlan,HttpStatus.OK);			
		}
		return new ResponseEntity<String>("CitizenPlan Not Found",HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateCitizenPlan(@RequestBody CitizenPlan citizenPlan){
		String msg = citizenPlanService.update(citizenPlan);
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	@PutMapping("/delete/{cid}")
	public ResponseEntity<String> deleteCitizenPlan(@PathVariable Integer cid){
		String msg = citizenPlanService.deleteCitizenPlan(cid);
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	
	@GetMapping("/excel")
	public void generateExcelReport(HttpServletResponse response) throws Exception {
		
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=citizen_plan_info.xls";

		response.setHeader(headerKey, headerValue);
		
		citizenPlanService.exportExcel(response);
		
		response.flushBuffer();
	}
	
	@GetMapping("/pdf")
	public void generatePdfReport(HttpServletResponse response) throws DocumentException, IOException  {
		
		response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=citizens_plan_info_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        
        citizenPlanService.exportPdf(response);
	}
}
