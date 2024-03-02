package br.com.gabrielgmusskopf.askme.domain.impl;

import br.com.gabrielgmusskopf.askme.domain.GetAnsweredQuestionsService;
import br.com.gabrielgmusskopf.askme.infra.data.UserAnswerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetAnsweredQuestionsImpl implements GetAnsweredQuestionsService {

  private final UserAnswerRepository userAnswerRepository;
  private final UserAnswerMapper userAnswerMapper;

  @Override
  public List<AnsweredQuestionDTO> getAll() {
    return userAnswerRepository.findAll().stream()
        .map(ua -> new GetAnsweredQuestionsService.AnsweredQuestionDTO(
            userAnswerMapper.toDTO(ua.getQuestion()),
            userAnswerMapper.toDTO(ua.getAnswer()))
        ).toList();
  }

}
