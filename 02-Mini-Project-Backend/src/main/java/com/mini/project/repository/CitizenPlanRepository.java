package com.mini.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mini.project.model.CitizenPlan;


@Repository
public interface CitizenPlanRepository extends JpaRepository<CitizenPlan, Integer> {
	
	@Query("select distinct(planName) from CitizenPlan")
	List<String> getPlanNames();
	
	@Query("select distinct(planStatus) from CitizenPlan")
	List<String> getPlanStatuses();
}
