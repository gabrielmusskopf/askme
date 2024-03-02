package br.com.gabrielgmusskopf.askme.domain;

import br.com.gabrielgmusskopf.askme.model.Category;
import java.util.List;

public interface CreateCategoryService {

  Category create(CreateCategoryDTO createCategory);

  List<Category> findOrCreate(List<String> categories);

  record CreateCategoryDTO(String name) {
  }

}
