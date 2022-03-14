package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {


    Optional<Employee> findByEmailAndPassword(String email, String password);
}
