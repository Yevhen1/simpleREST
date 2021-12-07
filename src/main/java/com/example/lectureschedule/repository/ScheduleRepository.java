package com.example.lectureschedule.repository;

import com.example.lectureschedule.models.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {
}
