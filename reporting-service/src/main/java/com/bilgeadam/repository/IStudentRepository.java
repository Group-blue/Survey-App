package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentRepository extends JpaRepository<Student, Long> {
}
