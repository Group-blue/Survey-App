package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionCategoryRepository extends JpaRepository<QuestionCategory, Long> {
}
