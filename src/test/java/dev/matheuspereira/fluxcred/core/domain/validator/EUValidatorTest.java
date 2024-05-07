package dev.matheuspereira.fluxcred.core.domain.validator;

import dev.matheuspereira.fluxcred.core.domain.model.IdentifierType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EUValidatorTest {

  @Test
  void validEUIdentifierReturnsTrue() {
    assertTrue(EUValidator.isValid("42311815"));  // A soma de 4 + 5 é 9
  }

  @Test
  void invalidEUIdentifierReturnsFalse() {
    assertFalse(EUValidator.isValid("32423123")); // A soma de 1 + 3 não é 9
  }

  @Test
  void invalidEUIdentifierLongerLengthReturnsFalse() {
    assertFalse(EUValidator.isValid("421311819")); // Tem mais de 8 caracteres
  }

}