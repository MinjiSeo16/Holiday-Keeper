package com.example.holidaykeeper.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Holiday {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "holiday_year")
	private int year;

	private String name;
	private String localName;
	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	private Country country;

	public static Holiday of(Country country, int year, String name, String localName, LocalDate date) {
		Holiday holiday = new Holiday();
		holiday.country = country;
		holiday.year = year;
		holiday.name = name;
		holiday.localName = localName;
		holiday.date = date;
		return holiday;
	}
}
