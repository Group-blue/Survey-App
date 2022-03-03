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
public class Topic {
    @Id
    @SequenceGenerator(name = "sq_topic_id", sequenceName = "sq_topic_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "sq_topic_id")
    private long id;
    private String topicCode;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "topics")
    private Set<Teacher> teachers;
}
