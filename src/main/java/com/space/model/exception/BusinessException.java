package com.space.model.exception;

public abstract class BusinessException extends RuntimeException {
  private String message;
  private int errorCode;

  public BusinessException(String message, int errorCode) {
    this.message = message;
    this.errorCode = errorCode;
  }

  @Override
  public String toString() {
    return "BusinessException{" +
        "message='" + message + '\'' +
        ", errorCode=" + errorCode +
        '}';
  }

  public String getMessage() {
    return message;
  }

  public int getErrorCode() {
    return errorCode;
  }
}
