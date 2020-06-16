package com.sea.api.resp;

/**
 * Created by wanglh on 2020/6/16.
 */
public class ApiResp {
  public static ApiResp OK = new ApiResp(Status.OK);

  private final Status status;
  private final Object data;


  public ApiResp(Status status) {
    this(status, null);
  }

  public ApiResp(Status status, Object data) {
    this.status = status;
    this.data = data;
  }

  public static ApiResp error(Status status) {
    return new ApiResp(status);
  }

  public static ApiResp ok(Object data) {
    return new ApiResp(Status.OK, data);
  }
}
