package br.com.gabrielgmusskopf.askme.infra.in;

import br.com.gabrielgmusskopf.askme.domain.GetAnsweredQuestionsService;
import br.com.gabrielgmusskopf.askme.domain.GetAnsweredQuestionsService.AnsweredQuestionDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/history")
@RequiredArgsConstructor
class HistoryController {

  private final GetAnsweredQuestionsService getAnsweredQuestionsService;

  @GetMapping("/answers")
  public List<AnsweredQuestionDTO> consult() {
    return getAnsweredQuestionsService.getAll();
  }

}
