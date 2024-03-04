package br.com.gabrielgmusskopf.askme.infra.data;

import br.com.gabrielgmusskopf.askme.domain.enums.Level;
import br.com.gabrielgmusskopf.askme.model.Category;
import br.com.gabrielgmusskopf.askme.model.Question;
import br.com.gabrielgmusskopf.askme.model.UserAnswer;
import jakarta.persistence.criteria.Join;
import java.util.List;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class QuestionSpecification {

  public Specification<Question> hasLevel(Level level) {
    return (root, query, criteriaBuilder) -> {
      if (level == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("level"), level);
    };
  }

  public Specification<Question> hasAnyCategory(List<String> categories) {
    return (root, query, criteriaBuilder) -> {
      if (categories == null || categories.isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      Join<Category, Question> questionCategory = root.join("categories");
      return questionCategory.get("name").in(categories);
    };
  }

  public Specification<Question> isNotAnswered(Boolean answered) {
    return (root, query, cb) -> {
      if (answered == null) {
        return cb.conjunction();
      }
      final var subquery = query.subquery(UUID.class);
      subquery.select(subquery.from(UserAnswer.class).get("question").get("id"));

      if (BooleanUtils.isFalse(answered)) {
        return cb.not(root.get("id").in(subquery));
      }
      return root.get("id").in(subquery);
    };
  }

}
