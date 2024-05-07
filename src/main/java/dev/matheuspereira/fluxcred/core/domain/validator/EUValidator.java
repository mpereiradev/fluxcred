package dev.matheuspereira.fluxcred.core.domain.validator;

public class EUValidator {
  public static boolean isValid(String identifier) {
    try {
      int firstDigit = Character.getNumericValue(identifier.charAt(0));
      int lastDigit = Character.getNumericValue(identifier.charAt(identifier.length() - 1));
      return identifier.length() == 8 && (firstDigit + lastDigit) == 9;
    } catch (Exception e) {
      return false;
    }
  }
}
