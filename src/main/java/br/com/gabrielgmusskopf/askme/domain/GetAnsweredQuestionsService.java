package br.com.gabrielgmusskopf.askme.domain;

import br.com.gabrielgmusskopf.askme.domain.enums.Level;
import java.util.List;

public interface GetAnsweredQuestionsService {

  List<AnsweredQuestionDTO> getAll();

  record AnsweredQuestionDTO(QuestionDTO question, AnswerDTO answer) {
  }

  record QuestionDTO(String id, String text, Level level) {
  }

  record AnswerDTO(String id, String text, boolean rightAnswer) {
  }

}
