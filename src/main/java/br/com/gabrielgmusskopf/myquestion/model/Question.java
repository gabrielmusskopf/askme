package br.com.gabrielgmusskopf.myquestion.model;

import br.com.gabrielgmusskopf.myquestion.domain.enums.Level;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "question")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String text;
  @Enumerated(EnumType.STRING)
  private Level level;
  @ManyToMany
  @JoinTable(
      name = "question_category",
      joinColumns = @JoinColumn(name = "question_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private List<Category> categories;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Question(String text, List<Category> categories) {
    this.text = text;
    this.categories = categories;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

}