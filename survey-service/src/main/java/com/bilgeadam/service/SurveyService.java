package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveSurveyRequestDto;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.rabbitmq.model.MailNotification;
import com.bilgeadam.rabbitmq.producer.SendMailToUsersProducer;
import com.bilgeadam.repository.IStudentAnswersRepository;
import com.bilgeadam.repository.IStudentRepository;
import com.bilgeadam.repository.ISurveyRepository;
import com.bilgeadam.repository.ISurveyTemlateRepository;
import com.bilgeadam.repository.entity.*;
import com.bilgeadam.util.JwtSurveyTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class SurveyService {
    private final ISurveyRepository surveyRepository;
    private final ISurveyTemlateRepository surveyTemlateRepository;
    private final SendMailToUsersProducer producer;
    private final JwtSurveyTokenManager jwtSurveyTokenManager;
    private final IStudentRepository studentRepository;
    private final SurveyTemplateService surveyTemplateService;
    private final IStudentAnswersRepository studentAnswersRepository;

    public SurveyService(ISurveyRepository surveyRepository, ISurveyTemlateRepository surveyTemlateRepository, SendMailToUsersProducer producer, JwtSurveyTokenManager jwtSurveyTokenManager, IStudentRepository studentRepository, SurveyTemplateService surveyTemplateService, IStudentAnswersRepository studentAnswersRepository) {
        this.surveyRepository = surveyRepository;
        this.surveyTemlateRepository = surveyTemlateRepository;
        this.producer = producer;
        this.jwtSurveyTokenManager = jwtSurveyTokenManager;
        this.studentRepository = studentRepository;
        this.surveyTemplateService = surveyTemplateService;
        this.studentAnswersRepository = studentAnswersRepository;
    }

    public Survey save(Survey survey) {
        return surveyRepository.save(survey);
    }

    public List<ListAllSurveyResponseDto> listAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        List<ListAllSurveyResponseDto> dtos = new ArrayList<>();
        for (Survey survey : surveys) {
            dtos.add(ListAllSurveyResponseDto.builder()
                    .id(survey.getId())
                    .templateId(survey.getSurveyTemplate().getId())
                    .templateExplanation(survey.getSurveyTemplate().getExplanation())
                    .templateName(survey.getSurveyTemplate().getTemplateCode())
                    .endDate(survey.getEndDate())
                    .startDate(survey.getStartDate())
                    .build());
        }
        return dtos;
    }


    public long saveSurvey(SaveSurveyRequestDto saveSurveyRequestDto) {
        Optional<SurveyTemplate> surveyTemplate = surveyTemlateRepository.findById(saveSurveyRequestDto.getTemplateId());
        if (surveyTemplate.isPresent()) {
            SurveyTemplate tempSurveyTemplate = surveyTemplate.get();
            Survey survey;
            survey = Survey.builder()
                    .sequenceNumber(saveSurveyRequestDto.getSequenceNumber())
                    .startDate(saveSurveyRequestDto.getStartDate())
                    .endDate(saveSurveyRequestDto.getEndDate())
                    .surveyTemplate(tempSurveyTemplate)
                    .build();
            Survey surveyFromDb = save(survey);
            return surveyFromDb.getId();
        } else {
            return -1;
        }
    }

    public SurveyDetailResponseDto findSurveyById(long surveyId) {

        Optional<Survey> surveyOptional = surveyRepository.findById(surveyId);
        SurveyDetailResponseDto surveyDetailResponseDto;
        if (surveyOptional.isPresent()) {
            Survey survey = surveyOptional.get();

            CourseBasicResponseDto courseBasicResponseDto = CourseBasicResponseDto.builder()
                    .courseCode(survey.getCourse().getCourseCode())
                    .id(survey.getCourse().getId())
                    .build();

            surveyDetailResponseDto = SurveyDetailResponseDto.builder()
                    .templateId(survey.getSurveyTemplate().getId())
                    .sequenceNumber(survey.getSequenceNumber())
                    .startDate(survey.getStartDate())
                    .endDate(survey.getEndDate())
                    .courseBasicResponseDto(courseBasicResponseDto)
                    .build();
            return surveyDetailResponseDto;
        } else {
            return new SurveyDetailResponseDto();
        }
    }

    public void sendMailToUsers(long courseId, long surveyId) {
        MailNotification notification = MailNotification.builder().surveyId(surveyId).courseId(courseId).build();
        producer.sendNotificationUserServiceForMails(notification);
    }

    @Transactional
    public StudentSurveyResponseDto getStudentSurveyByToken(String token) {
        Student student;
        Survey survey;
        StudentSurveyResponseDto studentSurveyResponseDto;

        if (jwtSurveyTokenManager.validateToken(token)) {
            log.info("....token valid");
            Optional<Long> studentIdOptional = jwtSurveyTokenManager.getUserId(token);
            Optional<Long> surveyIdOptional = jwtSurveyTokenManager.getSurveyId(token);
            if (studentIdOptional.isPresent()) {
                long studentId = studentIdOptional.get();
                student = studentRepository.findById(studentId).get();

                if (surveyIdOptional.isPresent()) {
                    long surveyId = surveyIdOptional.get();

                    survey = surveyRepository.findById(surveyId).get();
                    Optional<StudentAnswers> studentAnswers= studentAnswersRepository.findByStudentAndSurvey(student,survey);
                    if (studentAnswers.isPresent()){
                        return StudentSurveyResponseDto.builder().status(900).build();
                    }

                    TemplateDetailsResponseDto templateDetailsResponseDto = surveyTemplateService.getTemplateDetailsById(survey.getSurveyTemplate().getId());
                    studentSurveyResponseDto = StudentSurveyResponseDto.builder().surveyTemplate(templateDetailsResponseDto)
                            .studentId(student.getId()).surveyId(survey.getId()).status(200).build();
                    return studentSurveyResponseDto;
                }
            }


            // => survey hangi kursa atanmış => student id li öğrenc, bu kursa kayıtlıysa ok bu surveyi görebilir.
            // kullanıcıya survey detayları dönülecek (studentId, surveyId, templateId, sorular(idleri ile birlikte) ve optionlar(idleri ile birlikte) )
        }
        return StudentSurveyResponseDto.builder().status(404).build();
    }
}