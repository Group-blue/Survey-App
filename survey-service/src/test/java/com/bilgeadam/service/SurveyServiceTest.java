package com.bilgeadam.service;

import com.bilgeadam.rabbitmq.producer.SendMailToUsersProducer;
import com.bilgeadam.repository.IStudentAnswersRepository;
import com.bilgeadam.repository.IStudentRepository;
import com.bilgeadam.repository.ISurveyRepository;
import com.bilgeadam.repository.ISurveyTemlateRepository;
import com.bilgeadam.repository.entity.Survey;
import com.bilgeadam.util.JwtSurveyTokenManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SurveyServiceTest {

    @InjectMocks
    private SurveyService surveyService;
    @InjectMocks
    private JwtSurveyTokenManager jwtSurveyTokenManager;

    @Mock
    private ISurveyRepository surveyRepository;
    @Mock
    private ISurveyTemlateRepository surveyTemlateRepository;
    @Mock
    private SendMailToUsersProducer producer;
    @Mock
    private IStudentRepository studentRepository;
    @Mock
    private SurveyTemplateService surveyTemplateService;
    @Mock
    private IStudentAnswersRepository studentAnswersRepository;

    @Test
    void save() {
        verify(surveyRepository).save(ArgumentMatchers.any(Survey.class));
    }

    @Test
    void listAllSurveys() {
    }
}