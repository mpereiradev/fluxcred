package dev.matheuspereira.fluxcred.infrastructure.entity;

import dev.matheuspereira.fluxcred.domain.model.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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

  @Column(nullable = false)
  private Integer personId;  // Chave estrangeira para a entidade Person

  @Column(nullable = false)
  private double amount;

  @Column(nullable = false)
  private int numberOfInstallments;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private LoanStatus status;

  @Column
  private LocalDateTime firstPaymentDate;

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

  @ManyToOne
  @JoinColumn(name = "person_id", referencedColumnName = "id", insertable = false, updatable = false)
  private PersonEntity person;
}
