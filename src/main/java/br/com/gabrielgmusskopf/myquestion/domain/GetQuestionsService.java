package br.com.gabrielgmusskopf.myquestion.domain;

import br.com.gabrielgmusskopf.myquestion.domain.enums.Level;
import br.com.gabrielgmusskopf.myquestion.model.Question;
import java.util.List;

public interface GetQuestionsService {

  Question get(String id);

  List<Question> get(GetQuestionsDTO getQuestions);

  record GetQuestionsDTO(int quantity, Level level, List<String> categories) {
  }

}
