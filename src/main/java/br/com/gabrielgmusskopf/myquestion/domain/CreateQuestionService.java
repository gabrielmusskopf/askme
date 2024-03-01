package br.com.gabrielgmusskopf.myquestion.domain;

import br.com.gabrielgmusskopf.myquestion.model.Question;
import java.util.List;

public interface CreateQuestionService {

  Question create(CreateQuestionDTO question);

  record CreateQuestionDTO(String text, List<CreateQuestionAnswserDTO> answers, List<String> categories) {

  }

  record CreateQuestionAnswserDTO(String text, boolean isRight) {

  }

}
