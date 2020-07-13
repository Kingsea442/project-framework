package com.sea.export.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sea.export.model.ExportTask;
import com.sea.export.service.TaskService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import org.springframework.stereotype.Service;

/**
 * 这里作为demo使用map去维护存储task，真实业务应该保存到自己的数据库中
 * Created by wanglh on 2020/7/13.
 */
@Service
public class TaskServiceImpl implements TaskService {

  // 代替数据库维护
  private Map<Long, ExportTask> taskMap = Maps.newConcurrentMap();
  private final LongAdder adder = new LongAdder();

  @Override
  public List<ExportTask> listAllTask() {
    return Lists.newArrayList(taskMap.values());
  }

  @Override
  public ExportTask getTask(long taskId) {
    return taskMap.get(taskId);
  }

  @Override
  public ExportTask addTask(ExportTask exportTask) {
    if (exportTask.getId() <= 0) {
      exportTask.setId(generateAutoIncrId());
    }
    taskMap.put(exportTask.getId(), exportTask);
    return taskMap.get(exportTask.getId());
  }

  @Override
  public ExportTask updateTask(ExportTask newTask, ExportTask oldTask) {
    taskMap.put(newTask.getId(), newTask);
    return taskMap.get(newTask.getId());
  }

  private long generateAutoIncrId() {
    adder.increment();
    return adder.longValue();
  }
}
