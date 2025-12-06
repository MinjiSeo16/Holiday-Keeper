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

@Service
@Transactional
@RequiredArgsConstructor
public class HolidayResyncService {

	private final HolidayRepository holidayRepository;
	private final CountryRepository countryRepository;
	private final RestTemplate restTemplate = new RestTemplate();

	private static final String HOLIDAY_API =
		"https://date.nager.at/api/v3/PublicHolidays/{year}/{country}";

	public void resync(String countryCode, int year) {

		// 1) 국가 코드 검증
		Country country = countryRepository.findByCountryCode(countryCode)
			.orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_COUNTRY));

		// 2) 기존 데이터 삭제 (해당 연도, 국가)
		holidayRepository.deleteByCountryAndYear(country, year);

		// 3) 외부 API 호출
		String url = HOLIDAY_API.replace("{year}", String.valueOf(year))
			.replace("{country}", countryCode);

		ExternalHolidayDto[] arr;
		try {
			arr = restTemplate.getForObject(url, ExternalHolidayDto[].class);
		} catch (Exception e) {
			throw new CustomException(ResponseCode.API_ERROR);
		}

		// 4) Holiday 엔티티 변환
		List<Holiday> holidays = Arrays.stream(arr)
			.map(h -> Holiday.of(
				country,
				year,
				h.name(),
				h.localName(),
				LocalDate.parse(h.date())
			))
			.toList();

		// 5) 저장
		holidayRepository.saveAll(holidays);
	}
}
