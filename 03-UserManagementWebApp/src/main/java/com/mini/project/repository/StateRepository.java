package com.mini.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mini.project.entity.State;

public interface StateRepository extends JpaRepository<State, Long> {
	
	//1st way
	//List<State> findByCountryId(Long countryId);
	
	//2nd way
	@Query("select s from State s where s.countryId=:countryId order by s.stateName")
	List<State> getAllStatesBasedOnCountryId(Long countryId);
	
}
