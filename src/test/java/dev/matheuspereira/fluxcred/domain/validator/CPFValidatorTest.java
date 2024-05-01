package dev.matheuspereira.fluxcred.domain.validator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CPFValidatorTest {

  @Test
  void validCPFReturnsTrue() {
    assertTrue(CPFValidator.isValid("52998224725")); // CPF válido
  }

  @Test
  void invalidCPFReturnsFalse() {
    assertFalse(CPFValidator.isValid("52998224726")); // CPF com dígitos verificadores incorretos
  }

  @Test
  void emptyCPFReturnsFalse() {
    assertFalse(CPFValidator.isValid("")); // CPF vazio
  }

  @Test
  void cpfWithNonNumericCharactersReturnsFalse() {
    assertFalse(CPFValidator.isValid("5299822472a")); // CPF com caracteres não numéricos
  }

  @Test
  void cpfWithInvalidLengthReturnsFalse() {
    assertFalse(CPFValidator.isValid("529982247")); // CPF com menos dígitos que o necessário
    assertFalse(CPFValidator.isValid("5299822472529")); // CPF com mais dígitos que o necessário
  }

  @Test
  void cpfWithAllIdenticalDigitsReturnsFalse() {
    assertFalse(CPFValidator.isValid("11111111111")); // CPF com todos os dígitos iguais
  }
}