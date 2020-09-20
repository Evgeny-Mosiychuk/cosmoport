package com.space.model.exception;

public class EntityNotFoundException extends BusinessException {

  private static final int errorCode = 404;

  public EntityNotFoundException(String message) {
    super(message, errorCode);
  }
}
