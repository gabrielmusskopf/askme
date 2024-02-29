package br.com.gabrielgmusskopf.myquestion.domain.impl;

import br.com.gabrielgmusskopf.myquestion.domain.GetAnswerService;
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
public class GetAnswerImpl implements GetAnswerService {

  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;

  @Override
  public List<Answer> getAll() {
    return answerRepository.findAll();
  }

  @Override
  public List<Answer> getByQuestionID(String questionId) {
    final var questionUuid = UUID.fromString(questionId);
    if (!questionRepository.existsById(questionUuid)) {
      throw new NotFoundException("Question '" + questionId + "' not found.");
    }

    return answerRepository.findByQuestionId(questionUuid);
  }
}
