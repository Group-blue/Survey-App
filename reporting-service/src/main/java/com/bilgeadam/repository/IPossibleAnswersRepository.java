package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.PossibleAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPossibleAnswersRepository extends JpaRepository<PossibleAnswers, Long> {
}
