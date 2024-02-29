package br.com.gabrielgmusskopf.myquestion.domain.impl;

import br.com.gabrielgmusskopf.myquestion.domain.CreateQuestionService;
import br.com.gabrielgmusskopf.myquestion.domain.exception.InvalidAnwserException;
import br.com.gabrielgmusskopf.myquestion.domain.exception.QuestionDuplicatedException;
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

  @Override
  @Transactional
  public Question create(CreateQuestionDTO createQuestion) {
    final var text = createQuestion.text().trim();
    if (questionRepository.existsByTextIgnoreCase(text)) {
      throw new QuestionDuplicatedException(
          "Question '" + createQuestion.text() + "' already exist.");
    }
    if (!exactOneCorrectAnswer(createQuestion)) {
      throw new InvalidAnwserException(
          "Must have exaclty one right answer.");
    }

    final var question = questionRepository.save(new Question(createQuestion.text()));
    final var answers = createQuestion.answers().stream()
        .map(a -> new Answer(a.text(), question, a.isRight()))
        .toList();

    answerRepository.saveAll(answers);
    return question;
  }

  private boolean exactOneCorrectAnswer(CreateQuestionDTO createQuestion) {
    final var rigth = createQuestion.answers().stream()
        .filter(CreateQuestionAnswserDTO::isRight)
        .toList();

    return rigth.size() == 1;
  }

}
