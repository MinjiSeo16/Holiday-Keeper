package com.example.holidaykeeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.holidaykeeper.domain.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}
