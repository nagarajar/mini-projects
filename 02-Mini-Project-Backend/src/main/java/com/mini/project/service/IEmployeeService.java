package com.mini.project.service;

import java.util.Date;
import java.util.List;

import com.mini.project.model.Employee;

public interface IEmployeeService {
	String save(Employee employee);
	List<Employee> getAllEmployees(String planName, String planStatus, Date startDate, Date endDate);
	Employee getOneEmployee(Integer empId);
	String update(Employee employee); 
	String deleteEmployee(Integer empId);
}
