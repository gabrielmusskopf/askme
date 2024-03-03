package br.com.gabrielgmusskopf.askme.domain.impl;

import br.com.gabrielgmusskopf.askme.domain.CreateCategoryService;
import br.com.gabrielgmusskopf.askme.domain.CreateQuestionService;
import br.com.gabrielgmusskopf.askme.domain.exception.InvalidAnwserException;
import br.com.gabrielgmusskopf.askme.domain.exception.InvalidQuestionException;
import br.com.gabrielgmusskopf.askme.infra.data.AnswerRepository;
import br.com.gabrielgmusskopf.askme.infra.data.QuestionRepository;
import br.com.gabrielgmusskopf.askme.model.Answer;
import br.com.gabrielgmusskopf.askme.model.Question;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class CreateQuestionImpl implements CreateQuestionService {

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

    log.debug("Creating question");
    final var categories = createCategoryService.findOrCreate(createQuestion.categories());
    final var question = questionRepository.save(
        new Question(createQuestion.text(), createQuestion.level(), categories)
    );
    final var answers = createQuestion.answers().stream()
        .map(a -> new Answer(a.text(), question, a.isRight()))
        .toList();

    answerRepository.saveAll(answers);
    log.info("Question {} created", question.getId());

    return question;
  }

  private boolean exactlyOneCorrectAnswer(CreateQuestionDTO createQuestion) {
    final var rigth = createQuestion.answers().stream()
        .filter(CreateQuestionAnswserDTO::isRight)
        .toList();

    return rigth.size() == 1;
  }

}
