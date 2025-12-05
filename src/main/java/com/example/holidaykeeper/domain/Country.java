package com.example.holidaykeeper.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String countryCode;
	private String countryName;

	public static Country of(String countryCode, String countryName) {
		Country country = new Country();
		country.countryCode = countryCode;
		country.countryName = countryName;
		return country;
	}
}
