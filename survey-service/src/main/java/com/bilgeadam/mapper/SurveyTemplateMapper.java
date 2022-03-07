package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.SaveOptionRequestDto;
import com.bilgeadam.dto.request.SaveQuestionRequestDto;
import com.bilgeadam.dto.response.ListAllTemplateResponseDto;
import com.bilgeadam.repository.entity.PossibleAnswers;
import com.bilgeadam.repository.entity.Question;
import com.bilgeadam.repository.entity.SurveyTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SurveyTemplateMapper {

    PossibleAnswers mapDtoToPossibleAnswers(SaveOptionRequestDto dto);

    Question mapQuestionDtoToQuestion(SaveQuestionRequestDto dto);

}
