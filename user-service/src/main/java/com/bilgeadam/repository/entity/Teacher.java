package com.bilgeadam.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
public class Teacher extends Employee{
    @Generated(GenerationTime.INSERT)
    @Column(columnDefinition = "bigserial")
    private long teacherId;
    private int yearsOfexperience;

    @OneToMany(mappedBy = "masterTrainer")
    private Set<Course> masterCourses;

    @OneToMany(mappedBy = "assistantTrainer")
    private Set<Course> assistanCourses;

    @ManyToMany
    @JoinTable(name = "teachers_topic", joinColumns = {
            @JoinColumn(name = "teacher_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "topic_id")
    })
    private Set<Topic> topics;
}
