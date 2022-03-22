package com.bilgeadam.service;


import com.bilgeadam.dto.response.StudentAnswersResponseDto;
import com.bilgeadam.dto.response.StudentQuestionAnswerResponseDto;
import com.bilgeadam.repository.IQuestionRepository;
import com.bilgeadam.repository.IStudentAnswersRepository;
import com.bilgeadam.repository.IStudentRepository;
import com.bilgeadam.repository.ISurveyRepository;
import com.bilgeadam.repository.entity.Question;
import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.repository.entity.StudentAnswers;
import com.bilgeadam.repository.entity.Survey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentAnswersService {

    IStudentRepository studentRepository;
    ISurveyRepository surveyRepository;
    IQuestionRepository questionRepository;
    IStudentAnswersRepository studentAnswersRepository;

    public StudentAnswersService(IStudentRepository studentRepository, ISurveyRepository surveyRepository, IQuestionRepository questionRepository, IStudentAnswersRepository studentAnswersRepository) {
        this.studentRepository = studentRepository;
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.studentAnswersRepository = studentAnswersRepository;
    }

    @Transactional
    public void save(StudentAnswersResponseDto dto) {
        long studentId = dto.getStudentId();
        long surveyId = dto.getSurveyId();
        boolean finished = dto.isFinished();
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        if (student.isPresent()) {
            Student studentDb = student.get();
            if (survey.isPresent()) {
                Survey surveyDb=survey.get();
                List<StudentQuestionAnswerResponseDto> studentQuestionAnswerResponseDtoList=dto.getAnswers();
                for (StudentQuestionAnswerResponseDto studentQuestionAnswerResponseDto:studentQuestionAnswerResponseDtoList
                     ) {
                    Optional<Question> question=questionRepository.findById(studentQuestionAnswerResponseDto.getQuestionId());
                    if (question.isPresent()){
                        Question questionDb=question.get();
                        StudentAnswers studentAnswers=StudentAnswers.builder().timestamp(new Date().getTime()).isFinished(finished)
                                .survey(surveyDb).student(studentDb).question(questionDb).type(studentQuestionAnswerResponseDto.getType())
                                .answer(studentQuestionAnswerResponseDto.getAnswer()).build();
                        studentAnswersRepository.save(studentAnswers);

                    }

                }
            }
        }


    }
}
