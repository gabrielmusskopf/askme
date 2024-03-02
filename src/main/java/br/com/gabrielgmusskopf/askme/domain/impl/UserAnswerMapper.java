package br.com.gabrielgmusskopf.askme.domain.impl;

import br.com.gabrielgmusskopf.askme.domain.GetAnsweredQuestionsService;
import br.com.gabrielgmusskopf.askme.model.Answer;
import br.com.gabrielgmusskopf.askme.model.Question;
import org.mapstruct.Mapper;

@Mapper
public interface UserAnswerMapper {

  GetAnsweredQuestionsService.AnswerDTO toDTO(Answer answer);

  GetAnsweredQuestionsService.QuestionDTO toDTO(Question question);

}
