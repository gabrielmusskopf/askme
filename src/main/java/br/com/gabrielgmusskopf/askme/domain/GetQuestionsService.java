package br.com.gabrielgmusskopf.askme.domain;

import br.com.gabrielgmusskopf.askme.domain.enums.Level;
import br.com.gabrielgmusskopf.askme.model.Question;
import java.util.List;

public interface GetQuestionsService {

  Question get(String id);

  List<Question> get(GetQuestionsParamsDTO getQuestions);

  record GetQuestionsParamsDTO(int quantity, Level level, List<String> categories, Boolean answered) {
  }

}
