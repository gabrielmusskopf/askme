package br.com.gabrielgmusskopf.askme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String text;
  @ManyToOne
  private Question question;
  private boolean rightAnswer;
  private LocalDateTime createdAt;

  public Answer(String text, Question question, boolean rightAnswer) {
    this.text = text;
    this.question = question;
    this.rightAnswer = rightAnswer;
    this.createdAt = LocalDateTime.now();
  }

}
