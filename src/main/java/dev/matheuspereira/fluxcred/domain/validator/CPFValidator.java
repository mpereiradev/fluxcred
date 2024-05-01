package dev.matheuspereira.fluxcred.domain.validator;

public class CPFValidator {
  public static boolean isValid(String cpf) {
    cpf = cpf.replaceAll("\\D", "");
    if (cpf.length() != 11 || cpf.matches(cpf.charAt(0) + "{11}")) return false;

    int[] weights = {10, 9, 8, 7, 6, 5, 4, 3, 2};
    int sum = 0;
    for (int i = 0; i < 9; i++) {
      sum += Integer.parseInt(cpf.substring(i, i + 1)) * weights[i];
    }
    int remaining = sum % 11;
    String firstCheckDigit = (remaining < 2) ? "0" : Integer.toString(11 - remaining);

    weights = new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    sum = 0;
    for (int i = 0; i < 10; i++) {
      sum += Integer.parseInt(cpf.substring(i, i + 1)) * weights[i];
    }
    remaining = sum % 11;
    String secondCheckDigit = (remaining < 2) ? "0" : Integer.toString(11 - remaining);

    return cpf.equals(cpf.substring(0, 9) + firstCheckDigit + secondCheckDigit);
  }
}
