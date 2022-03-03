package com.bilgeadam;

import com.bilgeadam.repository.*;
import com.bilgeadam.repository.entity.Branch;
import com.bilgeadam.repository.entity.Course;
import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.repository.entity.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@SpringBootApplication
public class UserServiceSpring implements CommandLineRunner {

    private final IBranchRepository iBranchRepository;
    private final ICourseRepository iCourseRepository;
    private final IEmployeeRepository iEmployeeRepository;
    private final IStudentRepository iStudentRepository;
    private final ITeacherRepository iTeacherRepository;
    private final ITopicRepository iTopicRepository;

    public UserServiceSpring(IBranchRepository iBranchRepository, ICourseRepository iCourseRepository,
                             IEmployeeRepository iEmployeeRepository, IStudentRepository iStudentRepository,
                             ITeacherRepository iTeacherRepository, ITopicRepository iTopicRepository) {
        this.iBranchRepository = iBranchRepository;
        this.iCourseRepository = iCourseRepository;
        this.iEmployeeRepository = iEmployeeRepository;
        this.iStudentRepository = iStudentRepository;
        this.iTeacherRepository = iTeacherRepository;
        this.iTopicRepository = iTopicRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserServiceSpring.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Student student = Student.builder().firstname("Ali").province("Ankara").build();
        Student student2 = Student.builder().firstname("Veli").province("Istanbul").build();
        iStudentRepository.save(student);
        iStudentRepository.save(student2);

        Teacher teacher = Teacher.builder().firstname("Hamit").build();
        Teacher teacher2 = Teacher.builder().firstname("Hamit").lastname("Mizrak").build();
        iTeacherRepository.save(teacher);
        iTeacherRepository.save(teacher2);

        Branch branch = Branch.builder().name("Kadikoy").build();
        iBranchRepository.save(branch);

        Set<Student> students = new HashSet<>();
        students.add(student);
        students.add(student2);


        Course course = Course.builder().name("Java").branch(branch).masterTrainer(teacher).assistantTrainer(teacher2).students(students).build();
        iCourseRepository.save(course);


    }
}
