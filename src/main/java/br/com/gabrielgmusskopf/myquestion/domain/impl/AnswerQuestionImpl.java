package br.com.gabrielgmusskopf.myquestion.domain.impl;

import br.com.gabrielgmusskopf.myquestion.domain.AnswerQuestionService;
import br.com.gabrielgmusskopf.myquestion.domain.exception.InvalidAnwserException;
import br.com.gabrielgmusskopf.myquestion.domain.exception.NotFoundException;
import br.com.gabrielgmusskopf.myquestion.infra.data.AnswerRepository;
import br.com.gabrielgmusskopf.myquestion.infra.data.QuestionRepository;
import br.com.gabrielgmusskopf.myquestion.model.Answer;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerQuestionImpl implements AnswerQuestionService {

  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;

  @Override
  public void answer(QuestionAnswerDTO questionAnswer) {
    final var questionUuid = UUID.fromString(questionAnswer.questionId());
    final var answerUuid = UUID.fromString(questionAnswer.answerId());

    final var question = questionRepository.findById(questionUuid)
        .orElseThrow(() -> new NotFoundException("Question '" + questionAnswer.questionId() + "' not found."));

    final var questionAnswers = answerRepository.findByQuestionId(questionUuid);
    if (doesQuestionNotHaveThisAnswer(questionAnswers, answerUuid)) {
      throw new InvalidAnwserException("Question '" + questionUuid + "' does not have this answer.");
    }

    return null;
  }

  private boolean doesQuestionNotHaveThisAnswer(List<Answer> questionAnswers, UUID answerUuid) {
    return questionAnswers.stream().noneMatch(qa -> answerUuid.equals(qa.getId()));
  }
}
