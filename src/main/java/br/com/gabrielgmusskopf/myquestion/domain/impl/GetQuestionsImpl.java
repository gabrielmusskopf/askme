package br.com.gabrielgmusskopf.myquestion.domain.impl;

import br.com.gabrielgmusskopf.myquestion.domain.GetQuestionsService;
import br.com.gabrielgmusskopf.myquestion.domain.exception.NotFoundException;
import br.com.gabrielgmusskopf.myquestion.domain.util.MathUtil;
import br.com.gabrielgmusskopf.myquestion.infra.data.QuestionRepository;
import br.com.gabrielgmusskopf.myquestion.infra.data.QuestionSpecification;
import br.com.gabrielgmusskopf.myquestion.model.Question;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
  public List<Question> get(GetQuestionsDTO getQuestions) {
    if (questionRepository.count() == 0) {
      return Collections.emptyList();
    }
    final var questionPage = getRandomQuestionPage(getQuestions);
    if (!questionPage.hasContent()) {
      return Collections.emptyList();
    }

    final var questions = new ArrayList<>(questionPage.getContent());
    Collections.shuffle(questions);

    return questions.subList(0, Math.min(getQuestions.quantity(), questions.size()));
  }

  private Page<Question> getRandomQuestionPage(GetQuestionsDTO getQuestions) {
    final var pageSize = Math.max(getQuestions.quantity(), DEFAULT_PAGE_SIZE);
    final var randomPage = (int) MathUtil.random(0, questionRepository.count() / pageSize);
    final var page = PageRequest.of(randomPage, pageSize);

    Specification<Question> spec = QuestionSpecification
        .hasLevel(getQuestions.level())
        .and(QuestionSpecification.hasAnyCategory(getQuestions.categories()));

    return questionRepository.findAll(spec, page);
  }

}
