package br.com.gabrielgmusskopf.askme.infra.data;

import br.com.gabrielgmusskopf.askme.model.UserAnswer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface UserAnswerRepository extends CrudRepository<UserAnswer, UUID>, JpaSpecificationExecutor<UserAnswer> {

  @NonNull
  @Override
  List<UserAnswer> findAll();

}
