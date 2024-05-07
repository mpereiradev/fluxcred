package dev.matheuspereira.fluxcred.core.domain.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class APValidatorTest {
  @Test
  void validAPIdentifierReturnsTrue() {
    assertTrue(APValidator.isValid("1234567890")); // 0 não está nos outros dígitos
  }

  @Test
  void invalidAPIdentifierReturnsFalse() {
    assertFalse(APValidator.isValid("1923456789")); // 9 está presente nos outros dígitos
  }

  @Test
  void invalidAPIdentifierLongerLengthReturnsFalse() {
    assertFalse(APValidator.isValid("42131181905")); // Tem mais de 10 caracteres
  }
}