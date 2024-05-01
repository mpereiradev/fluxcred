package dev.matheuspereira.fluxcred.infrastructure.repository;

import dev.matheuspereira.fluxcred.domain.model.Loan;
import dev.matheuspereira.fluxcred.domain.ports.driven.ILoanRepository;
import dev.matheuspereira.fluxcred.infrastructure.entity.LoanEntity;
import dev.matheuspereira.fluxcred.infrastructure.jpa.LoanJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Repository
@RequiredArgsConstructor
public class LoanRepository implements ILoanRepository {
  private final LoanJpaRepository loanJpaRepository;
  private final ModelMapper modelMapper;

  @Override
  public Loan save(Loan loan) {
    LoanEntity loanEntity = loanJpaRepository
        .save(modelMapper.map(loan, LoanEntity.class));
    return modelMapper.map(loanEntity, Loan.class);
  }

  @Override
  public Optional<Loan> findById(Integer id) {
    return loanJpaRepository.findById(id)
        .map(l -> modelMapper.map(l, Loan.class));
  }

  @Override
  public boolean existsById(Integer id) {
    return loanJpaRepository.existsById(id);
  }

  @Override
  public void delete(Integer id) {
    loanJpaRepository.deleteById(id);
  }

}
