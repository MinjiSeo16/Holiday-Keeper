package com.example.holidaykeeper.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.holidaykeeper.domain.Country;
import com.example.holidaykeeper.domain.Holiday;
import com.example.holidaykeeper.dto.CountryDto;
import com.example.holidaykeeper.dto.ExternalHolidayDto;
import com.example.holidaykeeper.exception.CustomException;
import com.example.holidaykeeper.exception.ResponseCode;
import com.example.holidaykeeper.repository.CountryRepository;
import com.example.holidaykeeper.repository.HolidayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HolidayService {

	private final HolidayRepository holidayRepository;
	private final CountryRepository countryRepository;
	private final RestTemplate restTemplate = new RestTemplate();

	private static final String COUNTRY_API =
		"https://date.nager.at/api/v3/AvailableCountries";
	private static final String HOLIDAY_API =
		"https://date.nager.at/api/v3/PublicHolidays/{year}/{country}";

	private static int startYear = 2020;
	private static int endYear = 2025;

	// 1) 모든 국가 목록 불러오기
	private List<CountryDto> getAllCountries() {
		CountryDto[] result = restTemplate.getForObject(COUNTRY_API, CountryDto[].class);

		if (result == null)
			throw new CustomException(ResponseCode.API_ERROR);

		return Arrays.asList(result);
	}

	// 2) 특정 국가 + 특정 연도의 공휴일 불러오기
	private List<Holiday> getHoliday(Country country, int year) {
		String url = HOLIDAY_API.replace("{year}", String.valueOf(year))
			.replace("{country}", country.getCountryCode());

		ExternalHolidayDto[] arr;

		try {
			arr = restTemplate.getForObject(url, ExternalHolidayDto[].class);
		} catch (Exception e) {
			throw new CustomException(ResponseCode.API_ERROR);
		}

		return Arrays.stream(arr)
			.map(h -> Holiday.of(
				country,
				year,
				h.name(),
				h.localName(),
				LocalDate.parse(h.date())
			))
			.toList();
	}

	// 3) 전체 국가 x 5년 데이터 저장
	public void loadInitialData() {
		List<CountryDto> countries = getAllCountries();

		// Country 저장 + Map 캐싱
		Map<String, Country> countryMap = countries.parallelStream()
			.map(dto -> countryRepository.save(
				Country.of(dto.countryCode(), dto.countryName())
			))
			.collect(Collectors.toMap(Country::getCountryCode, c -> c));

		// Holiday 저장
		countries.parallelStream().forEach(dto -> {
			Country country = countryMap.get(dto.countryCode());

			IntStream.rangeClosed(startYear, endYear)
				.parallel()
				.forEach(year -> {
					List<Holiday> holidays = getHoliday(country, year);
					holidayRepository.saveAll(holidays);
				});
		});
	}
}
