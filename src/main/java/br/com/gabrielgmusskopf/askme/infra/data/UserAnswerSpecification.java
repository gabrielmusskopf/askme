package br.com.gabrielgmusskopf.askme.infra.data;

import br.com.gabrielgmusskopf.askme.domain.enums.Level;
import br.com.gabrielgmusskopf.askme.model.Category;
import br.com.gabrielgmusskopf.askme.model.Question;
import br.com.gabrielgmusskopf.askme.model.UserAnswer;
import jakarta.persistence.criteria.Join;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class UserAnswerSpecification {

  public Specification<UserAnswer> hasLevel(Level level) {
    return (root, query, criteriaBuilder) -> {
      if (level == null) {
        return criteriaBuilder.conjunction();
      }
      Join<Question, UserAnswer> questionCategory = root.join("question");
      return questionCategory.get("level").in(level);
    };
  }

  public Specification<UserAnswer> hasAnyCategory(List<String> categories) {
    return (root, query, criteriaBuilder) -> {
      if (categories == null || categories.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      Join<Category, UserAnswer> questionCategory = root.join("question").join("categories");
      return questionCategory.get("name").in(categories);
    };
  }

}
