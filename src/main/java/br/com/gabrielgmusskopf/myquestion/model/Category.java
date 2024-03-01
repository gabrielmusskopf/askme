package br.com.gabrielgmusskopf.myquestion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "category")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String name;
  private LocalDateTime createdAt;

  public Category(String name) {
    this.name = name;
    this.createdAt = LocalDateTime.now();
  }

}
