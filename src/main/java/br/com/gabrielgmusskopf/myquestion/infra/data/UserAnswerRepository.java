package br.com.gabrielgmusskopf.myquestion.infra.data;

import br.com.gabrielgmusskopf.myquestion.model.UserAnswer;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserAnswerRepository extends CrudRepository<UserAnswer, UUID> {

}
