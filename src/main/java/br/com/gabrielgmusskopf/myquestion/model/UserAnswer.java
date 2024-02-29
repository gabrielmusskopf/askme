package br.com.gabrielgmusskopf.myquestion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class UserAnswer {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
  @OneToOne
  @JoinColumn(name = "question_id")
  private Question question;

}
