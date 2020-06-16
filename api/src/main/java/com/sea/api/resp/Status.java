package com.sea.api.resp;

/**
 * Created by wanglh on 2020/6/16.
 */
public class Status {

  public static Status OK = new Status(0, "OK", "succeed");

  // 定义的错误码
  private final int code;
  // 定义的错误吗名称
  private final String name;
  // 具体的错误信息
  private String msg;

  public Status(int code) {
    this(code, String.valueOf(code), String.format("ERROR#%d", code));
  }

  public Status(int code, String name) {
    this(code, name, name);
  }

  public Status(int code, String name, String msg) {
    this.code = code;
    this.name = name;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
