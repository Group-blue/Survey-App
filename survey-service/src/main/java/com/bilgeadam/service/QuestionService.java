package com.bilgeadam.service;

import com.bilgeadam.repository.IQuestionRepository;
import com.bilgeadam.repository.entity.Question;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final IQuestionRepository questionRepository;

    public QuestionService(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> saveList(List<Question> questions) {
        return questionRepository.saveAll(questions);
    }
    public List<Question> getQuestionList(long id) {
        return questionRepository.findAllById(Collections.singleton(id));
    }
    public Optional<Question> findById(long id){
        return questionRepository.findById(id);
    }
}
