package br.com.gabrielgmusskopf.askme.domain.impl;

import br.com.gabrielgmusskopf.askme.domain.GetAnsweredQuestionsService;
import br.com.gabrielgmusskopf.askme.domain.dto.PagedContent;
import br.com.gabrielgmusskopf.askme.domain.util.CategoryUtil;
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

  private static final int MIN_PER_PAGE = 1;
  private static final int MAX_PER_PAGE = 200;

  private final UserAnswerRepository userAnswerRepository;
  private final UserAnswerMapper userAnswerMapper;

  @Override
  public PagedContent<List<AnsweredQuestionDTO>> get(GetAnsweredQuestionsDTO getAnsweredQuestions) {
    final int page = Math.max(MIN_PER_PAGE, getAnsweredQuestions.page());
    final int limit = boundLimits(getAnsweredQuestions);
    final var pageRequest = PageRequest.of(page - 1, limit);

    final var specification = UserAnswerSpecification
        .hasAnyCategory(CategoryUtil.normalizeAll(getAnsweredQuestions.categories()))
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

  private int boundLimits(GetAnsweredQuestionsDTO getAnsweredQuestions) {
    return Math.min(MAX_PER_PAGE, Math.max(MIN_PER_PAGE, getAnsweredQuestions.limit()));
  }

}
