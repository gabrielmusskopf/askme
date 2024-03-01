package br.com.gabrielgmusskopf.myquestion.domain;

import br.com.gabrielgmusskopf.myquestion.model.Category;
import java.util.List;

public interface CreateCategoryService {

  Category create(CreateCategoryDTO createCategory);

  List<Category> findOrCreate(List<String> categories);

  record CreateCategoryDTO(String name) {
  }

}
