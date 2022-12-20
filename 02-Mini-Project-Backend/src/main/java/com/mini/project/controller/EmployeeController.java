package com.mini.project.controller;

import java.util.Date;
import java.util.List;

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

import com.mini.project.model.Employee;
import com.mini.project.service.IEmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController 
{
	@Autowired
	private IEmployeeService employeeService;
	
	@PostMapping("/create")
	public ResponseEntity<String> createEmployee(@RequestBody Employee employee){
		String msg = employeeService.save(employee);
		return new ResponseEntity<String>(msg,HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Employee>> getEmployees(
			@RequestParam String planName, 
			@RequestParam String planStatus, 
			@RequestParam Date startDate, 
			@RequestParam Date endDate ){
		List<Employee> employees = employeeService.getAllEmployees(planName, planStatus, startDate, endDate);
		return new ResponseEntity<List<Employee>>(employees,HttpStatus.OK);
	}
	
	@GetMapping("/{empId}")
	public ResponseEntity<?> getOneEmployee(@PathVariable Integer empId){
		Employee employee = employeeService.getOneEmployee(empId);
		if(employee != null) {
			return new ResponseEntity<Employee>(employee,HttpStatus.OK);			
		}
		return new ResponseEntity<String>("Employee Not Found",HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateEmployee(@RequestBody Employee employee){
		String msg = employeeService.update(employee);
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	@PutMapping("/delete/{empId}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Integer empId){
		String msg = employeeService.deleteEmployee(empId);
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
}
