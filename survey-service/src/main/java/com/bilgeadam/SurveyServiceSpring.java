package com.bilgeadam;

import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.repository.entity.Survey;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class SurveyServiceSpring {
    public static void main(String[] args) {
        SpringApplication.run(SurveyServiceSpring.class, args);
    }
}