package br.com.gabrielgmusskopf.myquestion.infra.in;

import br.com.gabrielgmusskopf.myquestion.domain.CreateQuestionService;
import br.com.gabrielgmusskopf.myquestion.domain.CreateQuestionService.CreateQuestionDTO;
import br.com.gabrielgmusskopf.myquestion.domain.GetAnswerService;
import br.com.gabrielgmusskopf.myquestion.infra.IdDTO;
import br.com.gabrielgmusskopf.myquestion.model.Answer;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/questions")
@RequiredArgsConstructor
public class QuestionController {

  private final CreateQuestionService createQuestionService;
  private final GetAnswerService getAnswerService;

  @PostMapping
  public IdDTO create(@RequestBody @Valid CreateQuestionDTO createQuestion) {
    final var question = createQuestionService.create(createQuestion);
    return new IdDTO(question.getId());
  }

  @GetMapping("/{questionId}")
  public List<QuestionDTO> get(@PathVariable String questionId) {
    final var answers = getAnswerService.getByQuestionID(questionId);
    final List<QuestionDTO> questions = new ArrayList<>();

    answers.stream()
        .collect(Collectors.groupingBy(Answer::getQuestion))
        .forEach((q, qas) -> {
          var ans = qas.stream()
              .map(qa -> new AnswerDTO(qa.getId().toString(), qa.getText()))
              .toList();

          questions.add(new QuestionDTO(q.getId().toString(), q.getText(), ans));
        });

    return questions;
  }

  public void answer(@RequestBody @Valid QuestionAnswerDTO questionAnswer) {

  }

  record QuestionDTO(String id, String text, List<AnswerDTO> answers) {
  }

  record AnswerDTO(String id, String text) {
  }


}
