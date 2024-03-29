package br.com.gabrielgmusskopf.askme.domain.impl;

import br.com.gabrielgmusskopf.askme.domain.CreateCategoryService;
import br.com.gabrielgmusskopf.askme.domain.exception.InvalidCategoryException;
import br.com.gabrielgmusskopf.askme.domain.util.CategoryUtil;
import br.com.gabrielgmusskopf.askme.infra.data.CategoryRepository;
import br.com.gabrielgmusskopf.askme.model.Category;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public List<Category> findOrCreate(List<String> categories) {
    if (categories == null || categories.isEmpty()) {
      return Collections.emptyList();
    }
    log.debug("Finding or creating categories");
    final var existingCategories = categoryRepository.findByNameInIgnoreCase(CategoryUtil.normalizeAll(categories));
    final var existingCategoriesNames = existingCategories.stream().map(Category::getName).toList();

    final var newCategories = categories.stream()
        .filter(Objects::nonNull)
        .map(CategoryUtil::normalize)
        .filter(c -> !existingCategoriesNames.contains(c))
        .map(Category::new)
        .toList();

    if (!newCategories.isEmpty()) {
      categoryRepository.saveAll(newCategories);
      log.debug("New categories created: {}", newCategories.stream().map(Category::getName).toList());
    }

    return Stream.of(existingCategories, newCategories)
        .flatMap(Collection::stream)
        .toList();
  }

}
