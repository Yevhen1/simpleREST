package com.example.lectureschedule.repository;

import com.example.lectureschedule.models.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
