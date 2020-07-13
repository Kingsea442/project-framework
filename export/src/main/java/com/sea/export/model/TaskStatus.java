package com.sea.export.model;

/**
 * Created by wanglh on 2020/7/13.
 */
public enum TaskStatus {
  // 无
  TASK_NONE(0),
  // 创建
  TASK_CREATED(1),
  // 提交
  TASK_SUBMITTED(2),
  // 已启动
  TASK_STARTED(3),
  // 进行中
  TASK_IN_PROGRESS(4),
  // 错误
  TASK_ERROR(5),
  // 完成
  TASK_FINISHED(6);

  private int code;

  TaskStatus(int code) {
    this.code = code;
  }

  public static void main(String[] args) {
    System.out.println(TaskStatus.TASK_CREATED);
  }
}
