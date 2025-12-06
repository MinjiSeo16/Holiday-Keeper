package com.example.holidaykeeper.dto;

import java.time.LocalDate;

public record HolidaySearchCondition(
	String countryName,
	Integer year,
	LocalDate from,
	LocalDate to,
	Long lastId,
	int size
) {
}
