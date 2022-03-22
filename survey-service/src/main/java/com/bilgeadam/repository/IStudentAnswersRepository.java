package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.repository.entity.StudentAnswers;
import com.bilgeadam.repository.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IStudentAnswersRepository extends JpaRepository<StudentAnswers, Long> {

    Optional<StudentAnswers> findByStudentAndSurvey(Student student, Survey survey);
}
