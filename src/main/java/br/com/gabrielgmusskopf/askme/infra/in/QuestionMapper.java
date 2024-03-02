package br.com.gabrielgmusskopf.askme.infra.in;

import br.com.gabrielgmusskopf.askme.infra.in.QuestionController.AnswerDTO;
import br.com.gabrielgmusskopf.askme.infra.in.QuestionController.QuestionDTO;
import br.com.gabrielgmusskopf.askme.model.Category;
import br.com.gabrielgmusskopf.askme.model.Question;
import lombok.experimental.UtilityClass;

@UtilityClass
class QuestionMapper {

  QuestionDTO toDTO(Question q) {
    var answers = q.getAnswers().stream()
        .map(qa -> new AnswerDTO(qa.getId().toString(), qa.getText()))
        .toList();

    var categories = q.getCategories()
        .stream()
        .map(Category::getName)
        .toList();

    return new QuestionDTO(q.getId().toString(), q.getText(), categories, answers);
  }

}
