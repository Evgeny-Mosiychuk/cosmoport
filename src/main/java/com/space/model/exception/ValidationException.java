package com.space.model.exception;

public class ValidationException extends BusinessException {

  private static final int errorCode = 400;

  public ValidationException(String message) {
    super(message, errorCode);
  }
}
