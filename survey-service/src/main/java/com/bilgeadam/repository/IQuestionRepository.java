package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
}
