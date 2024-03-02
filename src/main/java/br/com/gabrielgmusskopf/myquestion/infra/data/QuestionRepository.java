package br.com.gabrielgmusskopf.myquestion.infra.data;

import br.com.gabrielgmusskopf.myquestion.domain.enums.Level;
import br.com.gabrielgmusskopf.myquestion.model.Question;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, UUID>, JpaSpecificationExecutor<Question> {

  long count();

  boolean existsByTextIgnoreCase(String text);

  Page<Question> findAllByLevel(Level level, Pageable page);

  Page<Question> findAll(Pageable page);

}
