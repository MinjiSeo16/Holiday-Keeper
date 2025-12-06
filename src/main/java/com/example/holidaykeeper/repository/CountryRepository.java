package com.example.holidaykeeper.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.holidaykeeper.domain.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
	Optional<Country> findByCountryCode(String countryCode);
}
