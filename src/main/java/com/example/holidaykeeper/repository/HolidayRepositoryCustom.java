package com.example.holidaykeeper.repository;

import com.example.holidaykeeper.dto.HolidaySearchCondition;
import com.example.holidaykeeper.dto.HolidaySliceResponse;

public interface HolidayRepositoryCustom {
	HolidaySliceResponse search(HolidaySearchCondition condition);
}
