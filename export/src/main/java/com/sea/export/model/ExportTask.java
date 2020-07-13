package com.sea.export.model;

import lombok.Data;

/**
 * 这个数据结构可以映射到自己数据库表中
 *
 * Created by wanglh on 2020/7/13.
 */
@Data
public class ExportTask {
  // 可以作为表中的主键
  private long id;
  // 自定义导出任务名称
  private String name;
  // 任务描述
  private String description;
  // 导出任务的配置, 可以自定义json文本，然后根据不同的业务类型去解析，比如时间段控制
  private String config;
  // 导出文件的类型，比如 .xlsx
  private String fileSuffix;
  // 任务执行状态
  private TaskStatus status;
  // 运行信息比如错误信息
  private String msg;
  // 导出文件资源path
  private String filePath;
  // 总条数
  private int totalItem;
  // 完成条数
  private int finishItem;
  // 所用时间ms
  private long cost;
  // 开始时间 时间戳
  private long startTime;
  // 结束时间
  private long finishTime;
}