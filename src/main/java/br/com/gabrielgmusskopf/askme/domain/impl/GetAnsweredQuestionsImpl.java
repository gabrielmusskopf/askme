package br.com.gabrielgmusskopf.askme.domain.impl;

import br.com.gabrielgmusskopf.askme.domain.GetAnsweredQuestionsService;
import br.com.gabrielgmusskopf.askme.domain.dto.PagedContent;
import br.com.gabrielgmusskopf.askme.infra.data.UserAnswerRepository;
import br.com.gabrielgmusskopf.askme.infra.data.UserAnswerSpecification;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetAnsweredQuestionsImpl implements GetAnsweredQuestionsService {

  private final UserAnswerRepository userAnswerRepository;
  private final UserAnswerMapper userAnswerMapper;

  @Override
  public PagedContent<List<AnsweredQuestionDTO>> get(GetAnsweredQuestionsDTO getAnsweredQuestions) {
    final int page = Math.max(1, getAnsweredQuestions.page());
    final int limit = Math.max(1, getAnsweredQuestions.limit());
    final var pageRequest = PageRequest.of(page - 1, limit);

    final var specification = UserAnswerSpecification
        .hasAnyCategory(getAnsweredQuestions.categories()) //TODO: normalize categories
        .and(UserAnswerSpecification.hasLevel(getAnsweredQuestions.level()));

    final var resultPaged = userAnswerRepository.findAll(specification, pageRequest);

    if (!resultPaged.hasContent()) {
      return PagedContent.empty(Collections.emptyList());
    }

    final var answers = resultPaged.getContent().stream()
        .map(ua -> new GetAnsweredQuestionsService.AnsweredQuestionDTO(
            userAnswerMapper.toDTO(ua.getQuestion()),
            userAnswerMapper.toDTO(ua.getAnswer()))
        ).toList();

    return new PagedContent<>(page, resultPaged.getTotalPages(), answers);
  }

}
