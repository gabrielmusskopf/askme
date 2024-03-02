package br.com.gabrielgmusskopf.myquestion.domain.impl;

import br.com.gabrielgmusskopf.myquestion.domain.CreateCategoryService;
import br.com.gabrielgmusskopf.myquestion.domain.CreateQuestionService;
import br.com.gabrielgmusskopf.myquestion.domain.exception.InvalidAnwserException;
import br.com.gabrielgmusskopf.myquestion.domain.exception.InvalidQuestionException;
import br.com.gabrielgmusskopf.myquestion.infra.data.AnswerRepository;
import br.com.gabrielgmusskopf.myquestion.infra.data.QuestionRepository;
import br.com.gabrielgmusskopf.myquestion.model.Answer;
import br.com.gabrielgmusskopf.myquestion.model.Question;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateQuestionImpl implements CreateQuestionService {

  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;
  private final CreateCategoryService createCategoryService;

  @Override
  @Transactional
  public Question create(CreateQuestionDTO createQuestion) {
    final var text = createQuestion.text().trim();
    if (questionRepository.existsByTextIgnoreCase(text)) {
      throw new InvalidQuestionException(
          "Question '" + createQuestion.text() + "' already exist.");
    }
    if (!exactlyOneCorrectAnswer(createQuestion)) {
      throw new InvalidAnwserException(
          "Must have exaclty one right answer.");
    }

    final var categories = createCategoryService.findOrCreate(createQuestion.categories());
    final var answers = createQuestion.answers().stream()
        .map(a -> new Answer(a.text(), a.isRight()))
        .toList();

    answerRepository.saveAll(answers);
    return questionRepository.save(new Question(createQuestion.text(), createQuestion.level(), answers, categories));
  }

  private boolean exactlyOneCorrectAnswer(CreateQuestionDTO createQuestion) {
    final var rigth = createQuestion.answers().stream()
        .filter(CreateQuestionAnswserDTO::isRight)
        .toList();

    return rigth.size() == 1;
  }

}
