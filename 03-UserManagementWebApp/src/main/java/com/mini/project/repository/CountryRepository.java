package com.mini.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mini.project.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
