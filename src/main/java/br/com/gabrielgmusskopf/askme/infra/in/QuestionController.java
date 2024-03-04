package br.com.gabrielgmusskopf.askme.infra.in;

import br.com.gabrielgmusskopf.askme.domain.AnswerQuestionService;
import br.com.gabrielgmusskopf.askme.domain.AnswerQuestionService.QuestionAnswerDTO;
import br.com.gabrielgmusskopf.askme.domain.CreateQuestionService;
import br.com.gabrielgmusskopf.askme.domain.CreateQuestionService.CreateQuestionDTO;
import br.com.gabrielgmusskopf.askme.domain.GetQuestionsService;
import br.com.gabrielgmusskopf.askme.domain.GetQuestionsService.GetQuestionsParamsDTO;
import br.com.gabrielgmusskopf.askme.domain.enums.Level;
import br.com.gabrielgmusskopf.askme.domain.exception.BusinessException;
import br.com.gabrielgmusskopf.askme.domain.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("v1/questions")
@RequiredArgsConstructor
class QuestionController {

  private final CreateQuestionService createQuestionService;
  private final GetQuestionsService getQuestionsService;
  private final AnswerQuestionService answerQuestionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public IDResponse create(@RequestBody @Valid CreateQuestionDTO createQuestion) {
    final var question = createQuestionService.create(createQuestion);
    return new IDResponse(question.getId());
  }

  @GetMapping("/{questionId}")
  public QuestionDTO get(@PathVariable String questionId) throws NotFoundException {
    return QuestionMapper.toDTO(getQuestionsService.get(questionId));
  }

  @GetMapping("/random")
  public List<QuestionDTO> getRandom(@RequestParam(name = "quantity", defaultValue = "1") int quantity,
                                     @RequestParam(name = "level", required = false) Level level,
                                     @RequestParam(name = "category", required = false) List<String> categories,
                                     @RequestParam(name = "answered", required = false) Boolean answered) {
    return getQuestionsService.get(new GetQuestionsParamsDTO(quantity, level, categories, answered))
        .stream()
        .map(QuestionMapper::toDTO)
        .toList();
  }

  @PostMapping("/{questionId}/answer")
  public void answer(@PathVariable String questionId, @RequestBody @Valid QuestionAnswerDTO questionAnswer)
      throws NotFoundException, BusinessException {
    answerQuestionService.answer(questionId, questionAnswer);
  }

  record QuestionDTO(String id, String text, List<String> categories, List<AnswerDTO> answers) {
  }

  record AnswerDTO(String id, String text) {
  }


}
