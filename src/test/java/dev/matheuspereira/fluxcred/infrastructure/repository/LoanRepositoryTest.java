package dev.matheuspereira.fluxcred.infrastructure.repository;

import dev.matheuspereira.fluxcred.domain.model.Loan;
import dev.matheuspereira.fluxcred.infrastructure.entity.LoanEntity;
import dev.matheuspereira.fluxcred.infrastructure.jpa.LoanJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanRepositoryTest {

  @Mock
  private LoanJpaRepository loanJpaRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private LoanRepository loanRepository;

  @Test
  void testSave() {
    Loan loan = new Loan();
    LoanEntity loanEntity = new LoanEntity();
    when(modelMapper.map(any(Loan.class), eq(LoanEntity.class))).thenReturn(loanEntity);
    when(loanJpaRepository.save(any(LoanEntity.class))).thenReturn(loanEntity);
    when(modelMapper.map(any(LoanEntity.class), eq(Loan.class))).thenReturn(loan);

    loanRepository.save(loan);

    verify(loanJpaRepository).save(loanEntity);
    verify(modelMapper).map(loan, LoanEntity.class);
    verify(modelMapper).map(loanEntity, Loan.class);
  }

  @Test
  void testSaveThrowsException() {
    Loan loan = new Loan();
    when(modelMapper.map(any(Loan.class), eq(LoanEntity.class))).thenThrow(new RuntimeException("Mapping failure"));

    assertThrows(RuntimeException.class, () -> loanRepository.save(loan));
    verify(loanJpaRepository, never()).save(any(LoanEntity.class));
  }


  @Test
  void testFindById() {
    Integer id = 1;
    Loan loan = new Loan();
    Optional<LoanEntity> loanEntity = Optional.of(new LoanEntity());
    when(loanJpaRepository.findById(id)).thenReturn(loanEntity);
    when(modelMapper.map(any(LoanEntity.class), eq(Loan.class))).thenReturn(loan);

    Optional<Loan> result = loanRepository.findById(id);

    verify(loanJpaRepository).findById(id);
    verify(modelMapper).map(loanEntity.get(), Loan.class);
  }

  @Test
  void testFindByIdWhenEmpty() {
    Integer id = 1;
    when(loanJpaRepository.findById(id)).thenReturn(Optional.empty());

    Optional<Loan> result = loanRepository.findById(id);

    assertTrue(result.isEmpty());
    verify(loanJpaRepository).findById(id);
    verify(modelMapper, never()).map(any(), eq(Loan.class));
  }

  @Test
  void testExistsById() {
    Integer id = 1;
    when(loanJpaRepository.existsById(id)).thenReturn(true);

    boolean exists = loanRepository.existsById(id);

    verify(loanJpaRepository).existsById(id);
    assertTrue(exists);
  }

  @Test
  void testExistsByIdWhenNotExists() {
    Integer id = 1;
    when(loanJpaRepository.existsById(id)).thenReturn(false);

    boolean exists = loanRepository.existsById(id);

    verify(loanJpaRepository).existsById(id);
    assertFalse(exists);
  }

  @Test
  void testDelete() {
    Integer id = 1;

    loanRepository.delete(id);

    verify(loanJpaRepository).deleteById(id);
  }

  @Test
  void testDeleteThrowsException() {
    doThrow(new RuntimeException("Delete failure")).when(loanJpaRepository).deleteById(any());

    Integer id = 1;
    assertThrows(RuntimeException.class, () -> loanRepository.delete(id));
    verify(loanJpaRepository).deleteById(id);
  }

}
