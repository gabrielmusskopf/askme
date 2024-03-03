package br.com.gabrielgmusskopf.askme.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.gabrielgmusskopf.askme.domain.CreateCategoryService.CreateCategoryDTO;
import br.com.gabrielgmusskopf.askme.domain.exception.InvalidCategoryException;
import br.com.gabrielgmusskopf.askme.infra.data.CategoryRepository;
import br.com.gabrielgmusskopf.askme.model.Category;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateCategoryImplTest {

  @Captor ArgumentCaptor<List<Category>> categoriesCaptor;
  @InjectMocks private CreateCategoryImpl service;
  @Mock private CategoryRepository categoryRepository;

  @Test
  void shouldNotCreateDuplicateCategory() {
    // Arrange
    final var category = new CreateCategoryDTO("Java");
    when(categoryRepository.existsByNameIgnoreCase("Java")).thenReturn(true);

    // Act & Assert
    assertThrows(InvalidCategoryException.class, () -> service.create(category));
  }

  @NullSource
  @ParameterizedTest
  void shouldReturnEmptyList_whenNoCategoriesProvided(List<String> categories) {
    final var response = service.findOrCreate(categories);
    assertTrue(response.isEmpty());
  }

  @Test
  void shouldCreateNormalizedNewCategories() {
    // Arange
    final var categories = List.of("Jávâ !@#$%¨&*())_+=- ", "Prǒgràmmíñg Lǻnguáge");
    final var savedCategoriesNames = List.of("JAVA", "PROGRAMMING_LANGUAGE");

    when(categoryRepository.findByNameInIgnoreCase(categories)).thenReturn(Collections.emptyList());

    // Act
    final var response = service.findOrCreate(categories);

    // Assert
    assertThat(response)
        .extracting(Category::getName)
        .containsExactlyInAnyOrderElementsOf(savedCategoriesNames);

    verify(categoryRepository).saveAll(categoriesCaptor.capture());
    final var savedCategories = categoriesCaptor.getValue();

    assertThat(savedCategories)
        .extracting(Category::getName)
        .containsExactlyInAnyOrderElementsOf(savedCategoriesNames);
  }

  @Test
  void shouldCreateOnlyNewCategories_andWithOldOnesMerged() {
    // Arrange
    final var categories = List.of("Jávâ", "Prǒgràmmíñg Lǻnguáge");
    final var savedCategoriesNames = List.of("PROGRAMMING_LANGUAGE");
    final var foundCategoriesNames = List.of("JAVA", "PROGRAMMING_LANGUAGE");
    final var existingCategories = List.of(new Category("JAVA"));

    when(categoryRepository.findByNameInIgnoreCase(categories)).thenReturn(existingCategories);

    // Act
    final var response = service.findOrCreate(categories);

    // Assert
    assertThat(response)
        .extracting(Category::getName)
        .containsExactlyInAnyOrderElementsOf(foundCategoriesNames);

    verify(categoryRepository).saveAll(categoriesCaptor.capture());
    final var savedCatedories = categoriesCaptor.getValue();

    assertThat(savedCatedories)
        .extracting(Category::getName)
        .containsExactlyInAnyOrderElementsOf(savedCategoriesNames);
  }

}