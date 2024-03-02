package br.com.gabrielgmusskopf.myquestion.domain.impl;

import br.com.gabrielgmusskopf.myquestion.domain.GetScoreService;
import br.com.gabrielgmusskopf.myquestion.infra.data.UserAnswerRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetScoreImpl implements GetScoreService {

  private static final Boolean RIGHT = Boolean.TRUE;
  private static final Boolean WRONG = Boolean.FALSE;

  private final UserAnswerRepository userAnswerRepository;

  @Override
  public ScoreDTO get() {
    final var userAnswers = userAnswerRepository.findAll();
    final var answerPartition = userAnswers.stream()
        .collect(Collectors.partitioningBy(ua -> ua.getAnswer().isRightAnswer()));

    return new ScoreDTO(userAnswers.size(), answerPartition.get(RIGHT).size(), answerPartition.get(WRONG).size());
  }
}
