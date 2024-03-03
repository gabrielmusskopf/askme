package br.com.gabrielgmusskopf.askme.domain;

import br.com.gabrielgmusskopf.askme.domain.enums.Level;
import java.util.List;

public interface GetAnsweredQuestionsService {

  PagedContent<List<AnsweredQuestionDTO>> get(GetAnsweredQuestionsDTO getAnsweredQuestions);

  record GetAnsweredQuestionsDTO(Level level, List<String> categories, int page, int limit) {
  }

  record AnsweredQuestionDTO(QuestionDTO question, AnswerDTO answer) {
  }

  record QuestionDTO(String id, String text, Level level) {
  }

  record AnswerDTO(String id, String text, boolean rightAnswer) {
  }

}
