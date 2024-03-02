package br.com.gabrielgmusskopf.askme.infra.data;

import br.com.gabrielgmusskopf.askme.model.Answer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface AnswerRepository extends CrudRepository<Answer, UUID> {

  List<Answer> findByQuestionId(UUID questionId);

  @NonNull
  @Override
  List<Answer> findAll();

}
