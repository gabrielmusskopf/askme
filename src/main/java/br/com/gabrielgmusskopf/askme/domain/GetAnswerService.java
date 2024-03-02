package br.com.gabrielgmusskopf.askme.domain;

import br.com.gabrielgmusskopf.askme.model.Answer;
import java.util.List;

public interface GetAnswerService {

  List<Answer> getAll();

  List<Answer> getByQuestionID(String questionId);

}
