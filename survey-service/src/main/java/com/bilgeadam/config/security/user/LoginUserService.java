package com.bilgeadam.config.security.user;

import com.bilgeadam.config.security.JwtTokenManager;
import com.bilgeadam.repository.IEmployeeRepository;
import com.bilgeadam.repository.ITeacherRepository;
import com.bilgeadam.repository.entity.Employee;
import com.bilgeadam.repository.entity.Teacher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserService implements UserDetailsService {
    private final IEmployeeRepository employeeRepository;
    private final ITeacherRepository teacherRepository;
    private final JwtTokenManager jwtTokenManager;

    public LoginUserService(IEmployeeRepository employeeRepository, ITeacherRepository teacherRepository, JwtTokenManager jwtTokenManager) {
        this.employeeRepository = employeeRepository;
        this.teacherRepository = teacherRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserById(long id) throws UsernameNotFoundException{
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            return LoginUser.builder().id(employee.getId()).email(employee.getEmail()).password(employee.getPassword()).build();
        }

        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        if (teacherOptional.isPresent()){
            Teacher teacher = teacherOptional.get();
            return LoginUser.builder().id(teacher.getId()).email(teacher.getEmail()).password(teacher.getPassword()).build();
        }

        throw new UsernameNotFoundException("User not found");
    }
}
