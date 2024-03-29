package br.com.gabrielgmusskopf.askme.domain.impl;

import br.com.gabrielgmusskopf.askme.domain.AnswerQuestionService;
import br.com.gabrielgmusskopf.askme.domain.exception.InvalidAnwserException;
import br.com.gabrielgmusskopf.askme.domain.exception.NotFoundException;
import br.com.gabrielgmusskopf.askme.infra.data.AnswerRepository;
import br.com.gabrielgmusskopf.askme.infra.data.QuestionRepository;
import br.com.gabrielgmusskopf.askme.infra.data.UserAnswerRepository;
import br.com.gabrielgmusskopf.askme.model.Answer;
import br.com.gabrielgmusskopf.askme.model.UserAnswer;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class AnswerQuestionImpl implements AnswerQuestionService {

  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;
  private final UserAnswerRepository userAnswerRepository;

  @Override
  public void answer(String questionId, QuestionAnswerDTO questionAnswer) {
    final var questionUuid = UUID.fromString(questionId);
    final var answerUuid = UUID.fromString(questionAnswer.answerId());
    final var question = questionRepository.findById(questionUuid)
        .orElseThrow(() -> new NotFoundException("Question not found."));

    if (doesQuestionNotHaveThisAnswer(question.getAnswers(), answerUuid)) {
      throw new InvalidAnwserException("Question does not have this answer.");
    }
    log.debug("Answering question {}", questionId);

    final var matchedAnswer = question.getAnswers().stream()
        .filter(qa -> qa.getId().equals(answerUuid))
        .findFirst()
        .orElseThrow();

    userAnswerRepository.save(new UserAnswer(question, matchedAnswer));
    log.info("Answered question {} with {}", questionId, matchedAnswer.getId());
  }

  private boolean doesQuestionNotHaveThisAnswer(List<Answer> questionAnswers, UUID answerUuid) {
    return questionAnswers.stream().noneMatch(qa -> answerUuid.equals(qa.getId()));
  }
}
