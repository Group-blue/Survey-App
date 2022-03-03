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
public class Survey {
    @Id
    @SequenceGenerator(name = "sq_survey_id", sequenceName = "sq_survey_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "sq_survey_id")
    private long id;
    private long sequenceNumber;
    private long startDate;
    private long endDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "survey_template_id")
    private SurveyTemplate surveyTemplate;

    @OneToMany(mappedBy = "survey")
    private Set<StudentAnswers> answers;
}
