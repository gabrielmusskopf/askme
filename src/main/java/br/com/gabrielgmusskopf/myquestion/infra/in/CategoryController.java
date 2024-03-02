package br.com.gabrielgmusskopf.myquestion.infra.in;

import br.com.gabrielgmusskopf.myquestion.domain.GetScoreService;
import br.com.gabrielgmusskopf.myquestion.domain.GetScoreService.ScoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/category")
@RequiredArgsConstructor
class CategoryController {

  private final GetScoreService getScoreService;

  @GetMapping
  public ScoreDTO consult() {
    return getScoreService.get();
  }


}
