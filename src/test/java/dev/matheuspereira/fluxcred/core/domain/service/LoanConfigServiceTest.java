package dev.matheuspereira.fluxcred.core.domain.service;

import dev.matheuspereira.fluxcred.core.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.core.domain.exception.NotFoundException;
import dev.matheuspereira.fluxcred.core.domain.model.IdentifierType;
import dev.matheuspereira.fluxcred.core.domain.model.LoanConfig;
import dev.matheuspereira.fluxcred.core.domain.ports.driven.ILoanConfigRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanConfigServiceTest {

  @Mock
  private ILoanConfigRepository loanConfigRepository;

  @InjectMocks
  private LoanConfigService loanConfigService;

  @Test
  void createShouldSaveWhenIdentifierTypeDoesNotExist() {
    LoanConfig loanConfig = new LoanConfig();
    loanConfig.setIdentifierType(IdentifierType.AP);
    when(loanConfigRepository.findByIdentifierType(any())).thenReturn(Optional.empty());
    when(loanConfigRepository.save(any())).thenReturn(loanConfig);

    LoanConfig created = loanConfigService.create(loanConfig);

    assertEquals(loanConfig, created);
    verify(loanConfigRepository).save(loanConfig);
  }

  @Test
  void createShouldThrowWhenIdentifierTypeExists() {
    LoanConfig loanConfig = new LoanConfig();
    loanConfig.setIdentifierType(IdentifierType.AP);

    when(loanConfigRepository.findByIdentifierType(any())).thenReturn(Optional.of(new LoanConfig()));

    assertThrows(BusinessException.class, () -> loanConfigService.create(loanConfig));
  }

  @Test
  void getByIdShouldReturnLoanConfigWhenFound() {
    LoanConfig loanConfig = new LoanConfig();
    when(loanConfigRepository.findById(any())).thenReturn(Optional.of(loanConfig));

    LoanConfig found = loanConfigService.getById(1);

    assertEquals(loanConfig, found);
  }

  @Test
  void getByIdShouldThrowNotFoundExceptionWhenNotFound() {
    when(loanConfigRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> loanConfigService.getById(1));
  }

  @Test
  void updateShouldSaveWhenValidRequest() {
    LoanConfig existing = new LoanConfig();
    LoanConfig patch = new LoanConfig();
    patch.setIdentifierType(IdentifierType.AP);

    when(loanConfigRepository.findById(any())).thenReturn(Optional.of(existing));
    when(loanConfigRepository.save(any())).thenReturn(existing);

    LoanConfig updated = loanConfigService.update(1, patch);

    assertEquals(existing, updated);
    verify(loanConfigRepository).save(existing);
  }

  @Test
  void updateShouldThrowWhenLoanConfigDoesNotExist() {
    when(loanConfigRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(BusinessException.class, () -> loanConfigService.update(1, new LoanConfig()));
  }

  @Test
  void updateShouldThrowWhenIdentifierTypeAlreadyExists() {
    LoanConfig existing = new LoanConfig();
    LoanConfig patch = new LoanConfig();
    patch.setIdentifierType(IdentifierType.EU);
    existing.setIdentifierType(IdentifierType.AP);

    when(loanConfigRepository.findById(any())).thenReturn(Optional.of(patch));
    when(loanConfigRepository.findByIdentifierType(any())).thenReturn(Optional.of(existing));

    assertThrows(BusinessException.class, () -> loanConfigService.update(1, patch));
  }

  @Test
  void deleteShouldCallRepositoryDelete() {
    loanConfigService.delete(1);

    verify(loanConfigRepository).delete(1);
  }
}
