package com.example.holidaykeeper.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.holidaykeeper.dto.HolidaySearchCondition;
import com.example.holidaykeeper.dto.HolidaySliceResponse;
import com.example.holidaykeeper.repository.HolidayRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HolidaySearchService {

	private final HolidayRepositoryCustom holidayRepositoryCustom;

	public HolidaySliceResponse search(HolidaySearchCondition condition) {
		return holidayRepositoryCustom.search(condition);
	}
}
