package com.sea.api.exception;

/**
 * 异常错误码封装
 * Created by wanglh on 2020/6/17.
 */
public enum ApiError {
  OK(0, "OK"),
  UNKNOWN_ERROR(1, "UNKNOWN_ERROR"),

  BAD_REQUEST(100, "BAD_REQUEST"),
  NOT_LOGIN(101, "NOT_LOGIN"),
  ACCESS_DENIED(102, "ACCESS_DENIED"),
  INVALID_CREDENTIALS(103, "INVALID_CREDENTIALS"),
  ILLEGAL_STATE(104, "ILLEGAL_STATE"),
  ILLEGAL_ARGUMENT(105, "ILLEGAL_ARGUMENT"),
  INTERNAL_ERROR(106, "INTERNAL_ERROR"),
  IO_ERROR(107, "IO_ERROR"),

  DATA_NOT_FOUND(120, "DATA_NOT_FOUND"),
  DATA_DUPLICATED(121, "DATA_DUPLICATED"),
  LIMIT_EXCEEDED(122, "LIMIT_EXCEEDED");



  private int code;
  private String description;


  ApiError(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public int getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public static void main(String[] args) {
    System.out.println(ApiError.OK.getCode());
  }
}
