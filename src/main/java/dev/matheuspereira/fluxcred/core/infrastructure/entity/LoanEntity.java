package dev.matheuspereira.fluxcred.core.infrastructure.entity;

import dev.matheuspereira.fluxcred.core.domain.model.LoanStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loans")
public class LoanEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, name = "person_identifier")
  private String personIdentifier;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(nullable = false)
  private int numberOfInstallments;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private LoanStatus status;

  @Column
  private LocalDate firstPaymentDate;

  @Column
  private LocalDateTime approvalDate;

  @Column
  private LocalDateTime signingDate;

  @Column
  private LocalDateTime delinquencyDate;

  @Column
  private LocalDateTime nextPaymentDate;

  @Column
  private LocalDateTime completionDate;

  @Column
  private LocalDateTime cancellationDate;

  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;
}
