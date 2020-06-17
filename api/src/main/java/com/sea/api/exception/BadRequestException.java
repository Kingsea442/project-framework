package com.sea.api.exception;

/**
 * Created by wanglh on 2020/6/17.
 */
public class BadRequestException extends RuntimeException {

  public BadRequestException(String msg) {
    super(msg);
  }

  public BadRequestException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public BadRequestException(String param, Object arguement) {
    this(String.format("invalid parameter '%s'=%s", param, arguement));
  }

}
