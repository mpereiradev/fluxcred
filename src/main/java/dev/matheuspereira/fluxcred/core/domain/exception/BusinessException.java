package dev.matheuspereira.fluxcred.core.domain.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class BusinessException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 2427882456503494680L;
  private final int httpStatus;
  private final String errorCode;

  public BusinessException(String message, int httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
    this.errorCode = "";
  }

  public BusinessException(String message, int httpStatus, String errorCode) {
    super(message);
    this.httpStatus = httpStatus;
    this.errorCode = errorCode;
  }
}
