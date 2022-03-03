package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISurveyRepository extends JpaRepository<Survey, Long> {
}
