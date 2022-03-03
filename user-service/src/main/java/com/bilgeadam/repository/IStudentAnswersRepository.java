package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.StudentAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentAnswersRepository extends JpaRepository<StudentAnswers, Long> {
}
