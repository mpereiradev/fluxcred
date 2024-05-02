package dev.matheuspereira.fluxcred.core.domain.service;

import dev.matheuspereira.fluxcred.core.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.core.domain.exception.NotFoundException;
import dev.matheuspereira.fluxcred.core.domain.model.IdentifierType;
import dev.matheuspereira.fluxcred.core.domain.model.LoanConfig;
import dev.matheuspereira.fluxcred.core.domain.ports.driven.ILoanConfigRepository;
import dev.matheuspereira.fluxcred.core.domain.ports.driver.ILoanConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanConfigService implements ILoanConfigService {
  private final ILoanConfigRepository loanConfigRepository;

  @Override
  public LoanConfig create(LoanConfig loanConfig) {
    loanConfigRepository.findByIdentifierType(loanConfig.getIdentifierType())
        .ifPresent(p -> {
          throw new BusinessException("The identifierType already exists", 422);
        });

    try {
      return loanConfigRepository.save(loanConfig);
    } catch (Exception e) {
      log.error("LoanConfig not saved", e);
      throw new BusinessException("Could not save the loanConfig, there was an internal error", 500);
    }

  }

  @Override
  public LoanConfig getById(Integer id) {
    return loanConfigRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public LoanConfig getByIdentifierType(IdentifierType identifierType) {
    return loanConfigRepository.findByIdentifierType(identifierType).orElseThrow(NotFoundException::new);
  }

  @Override
  public LoanConfig update(Integer id, LoanConfig loanConfigPatch) {
    var loanConfig = loanConfigRepository.findById(id)
        .orElseThrow(() -> new BusinessException("The loanConfig not exists", 404));

    loanConfigRepository.findByIdentifierType(loanConfigPatch.getIdentifierType())
        .ifPresent(l -> {
          if (!l.getIdentifierType().name().equalsIgnoreCase(loanConfigPatch.getIdentifierType().name())) {
            throw new BusinessException("The identifierType already exists", 422);
          }
        });

    applyPatchToModel(loanConfigPatch, loanConfig);
    try {
      return loanConfigRepository.save(loanConfig);
    } catch (Exception e) {
      log.error("LoanConfig not saved", e);
      throw new BusinessException("Could not save the loanConfig, there was an internal error", 500);
    }
  }

  @Override
  public void delete(Integer id) {
    loanConfigRepository.delete(id);
  }

  private void applyPatchToModel(LoanConfig loanConfigPatch, LoanConfig loanConfig) {
    if (loanConfigPatch.getMinMonthlyPayment() > 0) {
      loanConfig.setMinMonthlyPayment(loanConfigPatch.getMinMonthlyPayment());
    }

    if (loanConfigPatch.getMaxLoanAmount() > 0) {
      loanConfig.setMaxLoanAmount(loanConfigPatch.getMaxLoanAmount());
    }
  }
}
