package br.com.gabrielgmusskopf.myquestion.domain;

import br.com.gabrielgmusskopf.myquestion.model.Answer;
import java.util.List;

public interface GetAnswerService {

  List<Answer> getAll();

  List<Answer> getByQuestionID(String questionId);

}
