package com.sea.api.exception;

/**
 * Created by wanglh on 2020/6/17.
 */
public class ApiException extends RuntimeException{
  // 错误状态码信息
  private final ApiError error;
  // 错误提示
  private String tip;

  public ApiException(ApiError error) {
    this.error = error;
    setTip(error.getDescription());
  }

  public ApiException(ApiError error, String tip) {
    this.error = error;
    setTip(tip);
  }

  public String getTip() {
    return this.tip;
  }

  public ApiException setTip(String tip) {
    this.tip = tip;
    return this;
  }

  public ApiError getError() {
    return error;
  }

  @Override
  public String getMessage() {
    return String.format("%s:%d:%s", this.error.name(), this.error.getCode(), getTip());
  }

  public static void main(String[] args) {
    throw new ApiException(ApiError.INTERNAL_ERROR, "what fuck problem");
  }
}
