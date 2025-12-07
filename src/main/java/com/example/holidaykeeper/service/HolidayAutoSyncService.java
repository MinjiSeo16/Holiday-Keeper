package com.example.holidaykeeper.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.holidaykeeper.domain.Country;
import com.example.holidaykeeper.domain.Holiday;
import com.example.holidaykeeper.dto.ExternalHolidayDto;
import com.example.holidaykeeper.exception.CustomException;
import com.example.holidaykeeper.exception.ResponseCode;
import com.example.holidaykeeper.repository.CountryRepository;
import com.example.holidaykeeper.repository.HolidayRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HolidayAutoSyncService {

	private final CountryRepository countryRepository;
	private final HolidayRepository holidayRepository;
	private final RestTemplate restTemplate = new RestTemplate();

	private static final String HOLIDAY_API =
		"https://date.nager.at/api/v3/PublicHolidays/{year}/{country}";

	/**
	 * 매년 1월 2일 01:00 KST 실행
	 * - 전년도: Full Sync (삭제 후 재조회)
	 * - 금년도: 이미 존재하면 삭제 후 재조회, 없으면 신규로 추가
	 */
	public void syncPreviousAndCurrentYear() {
		int currentYear = LocalDate.now().getYear();
		int previousYear = currentYear - 1;

		log.info("전체 국가 공휴일 자동 동기화 시작 previousYear={}, currentYear={}",
			previousYear, currentYear);

		List<Country> countries = countryRepository.findAll();

		for (Country country : countries) {
			// 전년도 → 항상 삭제 후 재저장
			syncCountryYear(country, previousYear);
			// 금년도 → 존재 여부 판단 후 Full Sync
			syncCountryYear(country, currentYear);
		}

		log.info("전체 국가 공휴일 자동 동기화 완료");
	}

	private void syncCountryYear(Country country, int year) {

		// 기존 데이터가 존재하면 삭제
		if (holidayRepository.existsByCountryAndYear(country, year)) {
			holidayRepository.deleteByCountryAndYear(country, year);
		}

		// 외부 API 호출
		String url = HOLIDAY_API.replace("{year}", String.valueOf(year))
			.replace("{country}", country.getCountryCode());

		ExternalHolidayDto[] arr;

		try {
			arr = restTemplate.getForObject(url, ExternalHolidayDto[].class);
		} catch (Exception e) {
			throw new CustomException(ResponseCode.API_ERROR);
		}

		List<Holiday> holidays = Arrays.stream(arr)
			.map(h -> Holiday.of(
				country,
				year,
				h.name(),
				h.localName(),
				LocalDate.parse(h.date())
			))
			.toList();

		holidayRepository.saveAll(holidays);

		log.info("동기화 완료 country={}, year={}, count={}",
			country.getCountryCode(), year, holidays.size());
	}
}
