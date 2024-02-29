package br.com.gabrielgmusskopf.myquestion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
  @JoinColumn(name = "question_id")
  private Question question;
  private boolean rightAnswer;

  public Answer(String text, Question question, boolean rightAnswer) {
    this.text = text;
    this.question = question;
    this.rightAnswer = rightAnswer;
  }

}
