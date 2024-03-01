package br.com.gabrielgmusskopf.myquestion.domain.impl;

import br.com.gabrielgmusskopf.myquestion.domain.CreateCategoryService;
import br.com.gabrielgmusskopf.myquestion.domain.exception.InvalidCategoryException;
import br.com.gabrielgmusskopf.myquestion.infra.data.CategoryRepository;
import br.com.gabrielgmusskopf.myquestion.model.Category;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CreateCategoryImpl implements CreateCategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public Category create(CreateCategoryDTO createCategory) {
    if (categoryRepository.existsByNameIgnoreCase(createCategory.name())) {
      throw new InvalidCategoryException("Category already exist.");
    }

    return categoryRepository.save(new Category(createCategory.name()));
  }

  @Override
  public List<Category> findOrCreate(List<String> categories) {
    if (categories == null || categories.isEmpty()) {
      return Collections.emptyList();
    }
    final var existingCategories = categoryRepository.findByNameInIgnoreCase(categories);
    final var existingCategoriesNames = existingCategories.stream().map(Category::getName).toList();

    final var newCategories = categories.stream()
        .filter(Objects::nonNull)
        .filter(c -> !existingCategoriesNames.contains(c))
        .map(this::normalizeCategory)
        .map(Category::new)
        .toList();

    categoryRepository.saveAll(newCategories);

    return Stream.of(existingCategories, newCategories)
        .flatMap(Collection::stream)
        .toList();
  }

  private String normalizeCategory(String category) {
    return Normalizer.normalize(category, Form.NFD)
        .replaceAll("\\p{M}", "")
        .replace("[^a-zA-Z0-9]", "")
        .toUpperCase()
        .replace(" ", "_").trim();
  }

}
