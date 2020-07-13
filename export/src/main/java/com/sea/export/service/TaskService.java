package com.sea.export.service;

import com.sea.export.model.ExportTask;
import java.util.List;

/**
 * Created by wanglh on 2020/7/13.
 */
public interface TaskService {

  List<ExportTask> listAllTask();

  ExportTask getTask(long taskId);

  ExportTask addTask(ExportTask exportTask);

  ExportTask updateTask(ExportTask newTask, ExportTask oldTask);

}
