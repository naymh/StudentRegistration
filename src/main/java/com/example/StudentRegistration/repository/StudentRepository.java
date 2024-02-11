package com.example.StudentRegistration.repository;

import com.example.StudentRegistration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
