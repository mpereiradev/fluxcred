package dev.matheuspereira.fluxcred.core.infrastructure.entity;

import dev.matheuspereira.fluxcred.core.domain.model.IdentifierType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "people")
public class PersonEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String identifier;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private IdentifierType identifierType;

  @Column(nullable = false)
  private double minMonthlyPayment;

  @Column(nullable = false)
  private BigDecimal maxLoanAmount;

  @Temporal(TemporalType.DATE)
  @Column(nullable = false)
  private LocalDate birthDate;

  @CreationTimestamp
  @Column(updatable = false, name = "created_at")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
