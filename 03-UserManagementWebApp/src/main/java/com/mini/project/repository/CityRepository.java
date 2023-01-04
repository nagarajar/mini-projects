package com.mini.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mini.project.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
	@Query("select c from City c where c.stateId=:stateId order by c.cityName")
	List<City> getAllCitiesBasedOnStateId(Long stateId);
}
