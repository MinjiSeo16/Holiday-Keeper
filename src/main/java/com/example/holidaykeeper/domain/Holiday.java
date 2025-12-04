package com.example.holidaykeeper.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Holiday {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String countryCode;

	@Column(name = "holiday_year")
	private int year;

	private String name;
	private String localName;
	private LocalDate date;

	public static Holiday of(String countryCode, int year, String name, String localName, LocalDate date) {
		Holiday holiday = new Holiday();
		holiday.countryCode = countryCode;
		holiday.year = year;
		holiday.name = name;
		holiday.localName = localName;
		holiday.date = date;
		return holiday;
	}
}
