package br.com.gabrielgmusskopf.myquestion.infra.data;

import br.com.gabrielgmusskopf.myquestion.model.Question;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, UUID> {

  boolean existsByTextIgnoreCase(String text);

}
