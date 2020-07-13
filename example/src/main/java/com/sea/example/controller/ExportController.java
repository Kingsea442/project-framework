package com.sea.example.controller;

import com.sea.api.resp.ApiResp;
import com.sea.example.export.task.runner.OrderExportTaskRunner;
import com.sea.export.TaskManager;
import com.sea.export.model.ExportTask;
import com.sea.export.model.TaskStatus;
import com.sea.export.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wanglh on 2020/7/13.
 */
@RestController
@RequestMapping(value = "/api/sea/v1/export/task")
public class ExportController {

  @Autowired
  private TaskService taskService;
  @Autowired
  private TaskManager manager;

  @GetMapping(value = "/list")
  public ApiResp listAllTask() {
    return ApiResp.ok(taskService.listAllTask());
  }

  /**
   * demo 导出订单
   * @return
   */
  @GetMapping(value = "/order/export")
  public ApiResp exportOrder() {
    ExportTask exportTask = new ExportTask();
    exportTask.setName("导出订单");
    exportTask.setFileSuffix(".xlsx");
    exportTask.setStatus(TaskStatus.TASK_CREATED);
    exportTask = taskService.addTask(exportTask);
    exportTask = manager.submit(new OrderExportTaskRunner(exportTask));
    return ApiResp.ok(exportTask);
  }
}
