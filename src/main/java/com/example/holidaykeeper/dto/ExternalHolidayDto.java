package com.example.holidaykeeper.dto;

public record ExternalHolidayDto (
	String name,
	String localName,
	String countryCode,
	String date
) {}
