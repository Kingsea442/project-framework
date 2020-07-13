package com.sea.export;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sea.export.model.ExportTask;
import com.sea.export.model.TaskStatus;
import com.sea.export.service.TaskService;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 任务管理
 * 负责任务的执行，调度，更新
 * Created by wanglh on 2020/7/13.
 */
@Component
@Slf4j
public class TaskManager implements InitializingBean {

  private File taskTmpDir = new File("/tmp/sea-export");
  private int threadNum = 10;
  private ExecutorService executor;
  private TaskProgressReporter reporter;
  private final Set<Long> taskIds = Sets.newConcurrentHashSet();

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private TaskService taskService;


  @Override
  public void afterPropertiesSet() throws Exception {
    if (!this.taskTmpDir.exists()) {
      this.taskTmpDir.mkdirs();
    }

    ThreadFactory factory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("export-task-%d")
        .setUncaughtExceptionHandler((t, e) -> log.warn(t.getName(), e)).build();
    Executors.newFixedThreadPool(threadNum, factory);
    this.executor = Executors.newFixedThreadPool(threadNum, factory);
    this.reporter = new TaskProgressReporter(this, 2);
  }

  public File tmpFile(ExportTask task) {
    if (!taskTmpDir.exists()) {
      taskTmpDir.mkdirs();
    }

    return new File(taskTmpDir, "task-" + task.getId() + task.getFileSuffix());
  }

  public String encodeConfig(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(Objects.toString(object), e);
    }
  }

  public <T> T decodeConfig(String configStr, Class<T> configClz) {
    try {
      return objectMapper.readValue(configStr, configClz);
    } catch (IOException e) {
      throw new RuntimeException("config: " + configStr + ", class=" + configClz, e);
    }
  }

  /**
   * 提交任务，任务的起点
   * @param taskRunner
   * @return
   */
  public ExportTask submit(TaskRunner taskRunner) {
    ExportTask task = taskRunner.getTask();
    synchronized (("submit-task-" + task.getId()).intern()) {
      if (taskIds.contains(task.getId())) {
        return task;
      }
      log.info("[submit] task-{} {}", task.getId(), task.getName());
      this.taskIds.add(task.getId());
      task.setStatus(TaskStatus.TASK_SUBMITTED);
      // todo 将task记录更新到数据中
      taskRunner.setManager(this);
      this.executor.submit(taskRunner);
      return task;
    }
  }

  public ExportTask started(ExportTask task) {
    log.info("[start] task-{} {}", task.getId(), task.getName());
    ExportTask newTask = task;
    newTask.setStatus(TaskStatus.TASK_STARTED);
    newTask.setStartTime(System.currentTimeMillis());
    return taskService.updateTask(newTask, task);
  }

  public ExportTask inProgress(ExportTask task, int total) {
    log.info("[inprogress] task-{} {}", task.getId(), task.getName());
    ExportTask newTask = task;
    newTask.setStatus(TaskStatus.TASK_IN_PROGRESS);
    newTask.setTotalItem(total);
    return taskService.updateTask(newTask, task);
  }

  public ExportTask finish(ExportTask task, String resPath) {
    log.info("[finish] task-{} {}", task.getId(), task.getName());
    ExportTask newTask = task;
    long now = System.currentTimeMillis();
    newTask.setStatus(TaskStatus.TASK_FINISHED);
    newTask.setMsg("export success");
    newTask.setFilePath(resPath);
    newTask.setFinishItem(task.getTotalItem());
    newTask.setFinishTime(now);
    newTask.setCost(now - task.getStartTime());
    return taskService.updateTask(newTask, task);
  }

  public ExportTask error(ExportTask task, Throwable throwable) {
    log.info("[error] task-{} {}", task.getId(), task.getName());
    ExportTask newTask = task;
    newTask.setStatus(TaskStatus.TASK_ERROR);
    newTask.setMsg(throwable.getClass().getName() + ": " + throwable.getMessage());
    return taskService.updateTask(newTask, task);
  }

  public void deltaProgress(long taskId, int delta) {
    this.reporter.delta(taskId, delta);
  }

  public ExportTask updateProgress(long taskId, int progress) {
    ExportTask task = taskService.getTask(taskId);
    ExportTask newTask = task;
    newTask.setFinishItem(progress);
    return taskService.updateTask(newTask, task);
  }

  public void remove(long taskId) {
    this.reporter.remove(taskId);
    this.taskIds.remove(taskId);
  }
}
