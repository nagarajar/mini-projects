package com.mini.project.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mini.project.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	List<Employee> findByEmpPlanName(String planName);
	List<Employee> findByEmpPlanNameAndEmpPlanStatus(String planName,String planStatus);
	List<Employee> findByEmpPlanNameAndEmpPlanStatusAndEmpStartDate(String planName,String planStatus, Date startDate);
	List<Employee> findByEmpPlanNameAndEmpPlanStatusAndEmpStartDateAndEmpEndDate(String planName,String planStatus, Date startDate, Date endDate);

}
