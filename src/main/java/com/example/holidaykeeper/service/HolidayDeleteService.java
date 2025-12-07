package com.example.holidaykeeper.service;

import org.springframework.stereotype.Service;

import com.example.holidaykeeper.domain.Country;
import com.example.holidaykeeper.exception.CustomException;
import com.example.holidaykeeper.exception.ResponseCode;
import com.example.holidaykeeper.repository.CountryRepository;
import com.example.holidaykeeper.repository.HolidayRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HolidayDeleteService {

	private final HolidayRepository holidayRepository;
	private final CountryRepository countryRepository;

	public void deleteByCountryAndYear(String countryCode, int year) {

		Country country = countryRepository.findByCountryCode(countryCode)
			.orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_COUNTRY));

		holidayRepository.deleteByCountryAndYear(country, year);
	}
}
