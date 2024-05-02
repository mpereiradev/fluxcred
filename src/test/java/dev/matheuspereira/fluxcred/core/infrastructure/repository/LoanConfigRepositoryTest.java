package dev.matheuspereira.fluxcred.core.infrastructure.repository;

import dev.matheuspereira.fluxcred.core.domain.model.IdentifierType;
import dev.matheuspereira.fluxcred.core.domain.model.LoanConfig;
import dev.matheuspereira.fluxcred.core.infrastructure.jpa.LoanConfigJpaRepository;
import dev.matheuspereira.fluxcred.core.infrastructure.entity.LoanConfigEntity;
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
class LoanConfigRepositoryTest {

  @Mock
  private LoanConfigJpaRepository loanConfigJpaRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private LoanConfigRepository loanConfigRepository;

  @Test
  void testSave() {
    LoanConfig loanConfig = new LoanConfig(); // Suppose LoanConfig is properly instantiated
    LoanConfigEntity loanConfigEntity = new LoanConfigEntity();
    when(modelMapper.map(any(LoanConfig.class), eq(LoanConfigEntity.class))).thenReturn(loanConfigEntity);
    when(loanConfigJpaRepository.save(any(LoanConfigEntity.class))).thenReturn(loanConfigEntity);
    when(modelMapper.map(any(LoanConfigEntity.class), eq(LoanConfig.class))).thenReturn(loanConfig);

    loanConfigRepository.save(loanConfig);

    verify(loanConfigJpaRepository).save(loanConfigEntity);
    verify(modelMapper).map(loanConfig, LoanConfigEntity.class);
    verify(modelMapper).map(loanConfigEntity, LoanConfig.class);
  }

  @Test
  void testFindById() {
    Integer id = 1;
    LoanConfig loanConfig = new LoanConfig();
    Optional<LoanConfigEntity> loanConfigEntity = Optional.of(new LoanConfigEntity());
    when(loanConfigJpaRepository.findById(id)).thenReturn(loanConfigEntity);
    when(modelMapper.map(any(LoanConfigEntity.class), eq(LoanConfig.class))).thenReturn(loanConfig);

    Optional<LoanConfig> result = loanConfigRepository.findById(id);

    verify(loanConfigJpaRepository).findById(id);
    verify(modelMapper).map(loanConfigEntity.get(), LoanConfig.class);
  }

  @Test
  void testSaveThrowsException() {
    LoanConfig loanConfig = new LoanConfig();
    when(modelMapper.map(any(LoanConfig.class), eq(LoanConfigEntity.class))).thenThrow(new RuntimeException("Mapping failure"));

    assertThrows(RuntimeException.class, () -> loanConfigRepository.save(loanConfig));
    verify(loanConfigJpaRepository, never()).save(any(LoanConfigEntity.class));
  }

  @Test
  void testFindByIdWhenEmpty() {
    Integer id = 1;
    when(loanConfigJpaRepository.findById(id)).thenReturn(Optional.empty());

    Optional<LoanConfig> result = loanConfigRepository.findById(id);

    assertTrue(result.isEmpty());
    verify(loanConfigJpaRepository).findById(id);
    verify(modelMapper, never()).map(any(), eq(LoanConfig.class));
  }

  @Test
  void testExistsById() {
    Integer id = 1;
    when(loanConfigJpaRepository.existsById(id)).thenReturn(true);

    boolean exists = loanConfigRepository.existsById(id);

    verify(loanConfigJpaRepository).existsById(id);
    assertTrue(exists);
  }

  @Test
  void testExistsByIdWheNotExists() {
    Integer id = 1;
    when(loanConfigJpaRepository.existsById(id)).thenReturn(false);

    boolean exists = loanConfigRepository.existsById(id);

    verify(loanConfigJpaRepository).existsById(id);
    assertFalse(exists);
  }

  @Test
  void testDelete() {
    Integer id = 1;

    loanConfigRepository.delete(id);

    verify(loanConfigJpaRepository).deleteById(id);
  }

  @Test
  void testDeleteThrowsException() {
    doThrow(new RuntimeException("Delete failure")).when(loanConfigJpaRepository).deleteById(any());

    Integer id = 1;
    assertThrows(RuntimeException.class, () -> loanConfigRepository.delete(id));
    verify(loanConfigJpaRepository).deleteById(id);
  }

  @Test
  void testFindByIdentifierType() {
    var identifierType = IdentifierType.PF;
    LoanConfig loanConfig = new LoanConfig();
    Optional<LoanConfigEntity> loanConfigEntity = Optional.of(new LoanConfigEntity());
    when(loanConfigJpaRepository.findByIdentifierType(identifierType)).thenReturn(loanConfigEntity);
    when(modelMapper.map(any(LoanConfigEntity.class), eq(LoanConfig.class))).thenReturn(loanConfig);

    loanConfigRepository.findByIdentifierType(identifierType);

    verify(loanConfigJpaRepository).findByIdentifierType(identifierType);
    verify(modelMapper).map(loanConfigEntity.get(), LoanConfig.class);
  }

  @Test
  void testFindByIdentifierTypeWhenEmpty() {
    var identifierType = IdentifierType.PJ;
    when(loanConfigJpaRepository.findByIdentifierType(identifierType)).thenReturn(Optional.empty());

    Optional<LoanConfig> result = loanConfigRepository.findByIdentifierType(identifierType);

    assertTrue(result.isEmpty());
    verify(loanConfigJpaRepository).findByIdentifierType(identifierType);
    verify(modelMapper, never()).map(any(), eq(LoanConfig.class));
  }
}
