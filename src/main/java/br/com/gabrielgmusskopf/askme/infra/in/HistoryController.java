package br.com.gabrielgmusskopf.askme.infra.in;

import br.com.gabrielgmusskopf.askme.domain.GetAnsweredQuestionsService;
import br.com.gabrielgmusskopf.askme.domain.GetAnsweredQuestionsService.AnsweredQuestionDTO;
import br.com.gabrielgmusskopf.askme.domain.GetAnsweredQuestionsService.GetAnsweredQuestionsDTO;
import br.com.gabrielgmusskopf.askme.domain.PagedContent;
import br.com.gabrielgmusskopf.askme.domain.enums.Level;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/history")
@RequiredArgsConstructor
class HistoryController {

  private final GetAnsweredQuestionsService getAnsweredQuestionsService;

  @GetMapping("/answers")
  public PagedContent<List<AnsweredQuestionDTO>> consult(
      @RequestParam(name = "category", required = false) List<String> categories,
      @RequestParam(name = "level", required = false) Level level,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "limit", defaultValue = "1") int limit) {
    final var getAnswerDTO = new GetAnsweredQuestionsDTO(level, categories, page, limit);
    return getAnsweredQuestionsService.get(getAnswerDTO);
  }

}
