package com.example.holidaykeeper.repository;

import static com.example.holidaykeeper.domain.QCountry.*;
import static com.example.holidaykeeper.domain.QHoliday.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.holidaykeeper.domain.Holiday;
import com.example.holidaykeeper.dto.HolidaySearchCondition;
import com.example.holidaykeeper.dto.HolidaySearchResponse;
import com.example.holidaykeeper.dto.HolidaySliceResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HolidayRepositoryImpl implements HolidayRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public HolidaySliceResponse search(HolidaySearchCondition condition) {

		BooleanBuilder builder = new BooleanBuilder();

		// --- 필터 조건들 ---
		if (condition.countryName() != null) {
			builder.and(country.countryName.containsIgnoreCase(condition.countryName()));
		}
		if (condition.year() != null) {
			builder.and(holiday.year.eq(condition.year()));
		}
		if (condition.from() != null && condition.to() != null) {
			builder.and(holiday.date.between(condition.from(), condition.to()));
		}

		// --- 커서 기반 ---
		if (condition.lastId() != null) {
			builder.and(holiday.id.gt(condition.lastId()));
		}

		int limit = condition.size() + 1;

		List<Holiday> result = queryFactory
			.selectFrom(holiday)
			.join(holiday.country, country).fetchJoin()
			.where(builder)
			.orderBy(holiday.id.asc())
			.limit(limit)
			.fetch();

		boolean hasNext = result.size() > condition.size();

		List<HolidaySearchResponse> content = result.stream()
			.limit(condition.size())
			.map(h -> new HolidaySearchResponse(
				h.getId(),
				h.getCountry().getCountryName(),
				h.getCountry().getCountryCode(),
				h.getYear(),
				h.getName(),
				h.getLocalName(),
				h.getDate()
			))
			.toList();

		return new HolidaySliceResponse(content, hasNext);
	}
}
