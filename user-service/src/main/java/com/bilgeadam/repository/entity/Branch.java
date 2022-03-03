package com.bilgeadam.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @SequenceGenerator(name = "sq_branch_id", sequenceName = "sq_branch_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "sq_branch_id")
    private long id;
    private String name;
    private String address;
    private String province;
    private String district;
    @OneToOne
    @JoinColumn
    private Employee manager;

    @OneToMany(mappedBy = "branch")
    private Set<Course> courses;

}
