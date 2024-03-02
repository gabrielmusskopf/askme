package br.com.gabrielgmusskopf.myquestion.domain;

import br.com.gabrielgmusskopf.myquestion.domain.enums.Level;
import br.com.gabrielgmusskopf.myquestion.model.Question;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public interface CreateQuestionService {

  Question create(CreateQuestionDTO question);

  record CreateQuestionDTO(@NotBlank String text,
                           @NotNull Level level,
                           @Size(min = 1) @NotNull @Valid List<CreateQuestionAnswserDTO> answers,
                           @Size(min = 1) @NotNull List<String> categories) {

  }

  record CreateQuestionAnswserDTO(@NotBlank String text, boolean isRight) {

  }

}
