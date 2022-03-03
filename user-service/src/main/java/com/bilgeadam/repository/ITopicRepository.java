package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITopicRepository extends JpaRepository<Topic, Long> {
}
