package br.com.gabrielgmusskopf.myquestion.infra.data;

import br.com.gabrielgmusskopf.myquestion.model.UserAnswer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface UserAnswerRepository extends CrudRepository<UserAnswer, UUID> {

  @NonNull
  @Override
  List<UserAnswer> findAll();


}
