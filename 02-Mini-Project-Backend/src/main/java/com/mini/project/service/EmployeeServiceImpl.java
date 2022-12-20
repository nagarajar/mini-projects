package com.mini.project.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.project.model.Employee;
import com.mini.project.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public String save(Employee employee) {		
		employee = employeeRepository.save(employee);
		if(employee.getEmpId() != null)
		{
			return "Employee Created Successfully";
		}
		else
		{			
			return "Employee Creation Failed";
		}
	}
	
	@Override
	public List<Employee> getAllEmployees(String planName, String planStatus, Date startDate, Date endDate) {
		if(planName != null && planStatus == null && startDate == null && endDate == null) {
			return employeeRepository.findByEmpPlanName(planName);
		}
		else if(planName != null && planStatus != null && startDate == null && endDate == null) {
			return employeeRepository.findByEmpPlanNameAndEmpPlanStatus(planName, planStatus);
		}
		else if(planName != null && planStatus != null && startDate != null && endDate == null) {
			return employeeRepository.findByEmpPlanNameAndEmpPlanStatusAndEmpStartDate
					(planName, planStatus, startDate);
		}
		else if(planName != null && planStatus != null && startDate != null && endDate != null) {
			return employeeRepository.findByEmpPlanNameAndEmpPlanStatusAndEmpStartDateAndEmpEndDate
					(planName, planStatus, startDate, endDate);
		}
		return employeeRepository.findAll();
	}

	@Override
	public Employee getOneEmployee(Integer empId) {
		
		Optional<Employee> findById = employeeRepository.findById(empId);
		if(findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public String update(Employee employee) {
		if(employeeRepository.existsById(employee.getEmpId()))
		{
			employee = employeeRepository.save(employee);
			return "Employee updated Successfully";
		}
		else {			
			return "Employee Not Found";
		}
	}

	@Override
	public String deleteEmployee(Integer empId) {
	
		if(employeeRepository.existsById(empId))
		{
			employeeRepository.deleteById(empId);
			return "Employee deleted Successfully";
		}
		return "Employee Not Found";
	}

}
