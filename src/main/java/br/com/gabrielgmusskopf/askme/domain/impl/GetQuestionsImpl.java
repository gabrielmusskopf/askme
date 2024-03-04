package br.com.gabrielgmusskopf.askme.domain.impl;

import br.com.gabrielgmusskopf.askme.domain.GetQuestionsService;
import br.com.gabrielgmusskopf.askme.domain.exception.NotFoundException;
import br.com.gabrielgmusskopf.askme.domain.util.CollectorUtil;
import br.com.gabrielgmusskopf.askme.domain.util.MathUtil;
import br.com.gabrielgmusskopf.askme.infra.data.QuestionRepository;
import br.com.gabrielgmusskopf.askme.infra.data.QuestionSpecification;
import br.com.gabrielgmusskopf.askme.model.Question;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class GetQuestionsImpl implements GetQuestionsService {

  private static final int DEFAULT_PAGE_SIZE = 20;

  private final QuestionRepository questionRepository;

  @Override
  public Question get(String id) {
    return questionRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new NotFoundException("Question '" + id + "' not found."));
  }

  @Override
  public List<Question> get(GetQuestionsParamsDTO params) {
    if (questionRepository.count() == 0) {
      return Collections.emptyList();
    }

    log.debug("Searching for {} questions with any of {} categories.", params.level(), params.categories());
    final var page = getRandomPage(params);
    final var questionPage = getQuestions(params, page);
    if (!questionPage.hasContent()) {
      return Collections.emptyList();
    }

    final var questions = questionPage.getContent().stream()
        .collect(CollectorUtil.toShuffledList());

    return questions.subList(0, Math.min(params.quantity(), questions.size()));
  }

  private Pageable getRandomPage(GetQuestionsParamsDTO params) {
    final int pageSize = Math.max(params.quantity(), DEFAULT_PAGE_SIZE);
    final long pagesNumber = questionRepository.count() / pageSize;
    final int randomPage = (int) MathUtil.random(0, pagesNumber);
    log.debug("Random page {} of {}", randomPage, pagesNumber);

    return PageRequest.of(randomPage, pageSize);
  }

  private Page<Question> getQuestions(GetQuestionsParamsDTO params, Pageable page) {
    Specification<Question> spec = QuestionSpecification
        .hasAnyCategory(params.categories())
        .and(QuestionSpecification.hasLevel(params.level()))
        .and(QuestionSpecification.isNotAnswered(params.answered()));

    return questionRepository.findAll(spec, page);
  }

}
