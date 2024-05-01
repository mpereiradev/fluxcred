package dev.matheuspereira.fluxcred.infrastructure.repository;

import dev.matheuspereira.fluxcred.domain.model.LoanConfig;
import dev.matheuspereira.fluxcred.domain.ports.driven.ILoanConfigRepository;
import dev.matheuspereira.fluxcred.infrastructure.entity.LoanConfigEntity;
import dev.matheuspereira.fluxcred.infrastructure.jpa.LoanConfigJpaRepository;
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
public class LoanConfigRepository implements ILoanConfigRepository {
  private final LoanConfigJpaRepository loanConfigJpaRepository;
  private final ModelMapper modelMapper;

  @Override
  public LoanConfig save(LoanConfig loanConfig) {
    LoanConfigEntity loanConfigEntity = loanConfigJpaRepository
        .save(modelMapper.map(loanConfig, LoanConfigEntity.class));
    return modelMapper.map(loanConfigEntity, LoanConfig.class);
  }

  @Override
  public Optional<LoanConfig> findById(Integer id) {
    return loanConfigJpaRepository.findById(id)
        .map(l -> modelMapper.map(l, LoanConfig.class));
  }

  @Override
  public boolean existsById(Integer id) {
    return loanConfigJpaRepository.existsById(id);
  }

  @Override
  public void delete(Integer id) {
    loanConfigJpaRepository.deleteById(id);
  }

  @Override
  public Optional<LoanConfig> findByIdentifierType(String identifierType) {
    return loanConfigJpaRepository.findByIdentifierType(identifierType)
        .map(l -> modelMapper.map(l, LoanConfig.class));
  }
}
