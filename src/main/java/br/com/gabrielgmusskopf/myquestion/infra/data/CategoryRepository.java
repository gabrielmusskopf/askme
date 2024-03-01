package br.com.gabrielgmusskopf.myquestion.infra.data;

import br.com.gabrielgmusskopf.myquestion.model.Category;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface CategoryRepository extends CrudRepository<Category, UUID> {

  boolean existsByNameIgnoreCase(String name);

  List<Category> findByNameIgnoreCase(String name);

  List<Category> findByNameInIgnoreCase(List<String> names);

  @NonNull
  @Override
  List<Category> findAll();


}
