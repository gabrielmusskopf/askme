package br.com.gabrielgmusskopf.myquestion.infra.in;

import br.com.gabrielgmusskopf.myquestion.domain.AnswerQuestionService;
import br.com.gabrielgmusskopf.myquestion.domain.AnswerQuestionService.QuestionAnswerDTO;
import br.com.gabrielgmusskopf.myquestion.domain.CreateQuestionService;
import br.com.gabrielgmusskopf.myquestion.domain.CreateQuestionService.CreateQuestionDTO;
import br.com.gabrielgmusskopf.myquestion.domain.GetQuestionsService;
import br.com.gabrielgmusskopf.myquestion.domain.GetQuestionsService.GetQuestionsDTO;
import br.com.gabrielgmusskopf.myquestion.domain.enums.Level;
import br.com.gabrielgmusskopf.myquestion.infra.IdDTO;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/questions")
@RequiredArgsConstructor
class QuestionController {

  private final CreateQuestionService createQuestionService;
  private final GetQuestionsService getQuestionsService;
  private final AnswerQuestionService answerQuestionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public IdDTO create(@RequestBody @Valid CreateQuestionDTO createQuestion) {
    final var question = createQuestionService.create(createQuestion);
    return new IdDTO(question.getId());
  }

  @GetMapping("/{questionId}")
  public QuestionDTO get(@PathVariable String questionId) {
    return QuestionMapper.toDTO(getQuestionsService.get(questionId));
  }

  @GetMapping("/random")
  public List<QuestionDTO> getRandom(@RequestParam(name = "quantity", defaultValue = "1") int quantity,
                                     @RequestParam(name = "level", required = false) Level level,
                                     @RequestParam(name = "category", required = false) List<String> categories) {
    return getQuestionsService.get(new GetQuestionsDTO(quantity, level, categories))
        .stream()
        .map(QuestionMapper::toDTO)
        .toList();
  }

  @PostMapping("/{questionId}/answer")
  public void answer(@PathVariable String questionId, @RequestBody @Valid QuestionAnswerDTO questionAnswer) {
    answerQuestionService.answer(questionId, questionAnswer);
  }

  record QuestionDTO(String id, String text, List<String> categories, List<AnswerDTO> answers) {
  }

  record AnswerDTO(String id, String text) {
  }


}
