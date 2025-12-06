package com.example.holidaykeeper.service;

import org.springframework.stereotype.Service;

import com.example.holidaykeeper.dto.HolidaySearchCondition;
import com.example.holidaykeeper.dto.HolidaySliceResponse;
import com.example.holidaykeeper.repository.HolidayRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HolidaySearchService {

	private final HolidayRepositoryCustom holidayRepositoryCustom;

	public HolidaySliceResponse search(HolidaySearchCondition condition) {
		return holidayRepositoryCustom.search(condition);
	}
}
