package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionTypeRepository extends JpaRepository<QuestionType, Long> {
}
