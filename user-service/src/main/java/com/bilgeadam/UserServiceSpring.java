package com.bilgeadam;

import com.bilgeadam.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceSpring {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceSpring.class, args);
    }
}
