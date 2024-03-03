package br.com.gabrielgmusskopf.askme.infra.data;

import br.com.gabrielgmusskopf.askme.domain.enums.Level;
import br.com.gabrielgmusskopf.askme.model.Category;
import br.com.gabrielgmusskopf.askme.model.Question;
import br.com.gabrielgmusskopf.askme.model.UserAnswer;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.List;
import lombok.experimental.UtilityClass;
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
      Subquery<Long> subquery = query.subquery(Long.class);
      Root<UserAnswer> userQuestionRoot = subquery.from(UserAnswer.class);
      subquery.select(cb.count(userQuestionRoot)).where(cb.equal(userQuestionRoot.get("question"), root));
      return cb.isNull(subquery);
    };
  }

}
