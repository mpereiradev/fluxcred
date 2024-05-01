package dev.matheuspereira.fluxcred.domain.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CNPJValidatorTest {

  @Test
  void validCNPJReturnsTrue() {
    assertTrue(CNPJValidator.isValid("13347016000117")); // CNPJ válido
    assertTrue(CNPJValidator.isValid("17750412000141")); // Outro CNPJ válido
  }

  @Test
  void invalidCNPJReturnsFalse() {
    assertFalse(CNPJValidator.isValid("13347016000118")); // CNPJ com dígitos verificadores incorretos
  }

  @Test
  void emptyCNPJReturnsFalse() {
    assertFalse(CNPJValidator.isValid("")); // CNPJ vazio
  }

  @Test
  void cnpjWithNonNumericCharactersReturnsFalse() {
    assertFalse(CNPJValidator.isValid("1334701600011a")); // CNPJ com caracteres não numéricos
  }

  @Test
  void cnpjWithInvalidLengthReturnsFalse() {
    assertFalse(CNPJValidator.isValid("133470160001")); // CNPJ com menos dígitos que o necessário
    assertFalse(CNPJValidator.isValid("13347016000117222")); // CNPJ com mais dígitos que o necessário
  }

  @Test
  void cnpjWithAllIdenticalDigitsReturnsFalse() {
    assertFalse(CNPJValidator.isValid("11111111111111")); // CNPJ com todos os dígitos iguais
  }
}