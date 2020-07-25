package com.sea.export;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by wanglh on 2020/7/13.
 */
@Slf4j
public class TaskProgressReporter implements Runnable{

  /**
   * concurrentHashMap防止多线程出问题
   */
  private final Map<Long, Progress> taskProgressMap = Maps.newConcurrentMap();
  /**
   * 使用ScheduleExecutorService自动执行报告进度
   */
  private final ScheduledExecutorService executor;
  private final TaskManager manager;

  public TaskProgressReporter(TaskManager manager, int periodSecs) {
    this.manager = manager;
    ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true)
        .setNameFormat(getClass().getSimpleName() + "-%d")
        .setUncaughtExceptionHandler((t, e) -> log.warn(t.getName(), e)).build();
    this.executor = Executors.newScheduledThreadPool(1, threadFactory);
    // 控制一定的时间执行一次去报告进度
    this.executor.scheduleWithFixedDelay(this, periodSecs, periodSecs, TimeUnit.SECONDS);
  }

  public void remove(long taskId) {
    Progress progress = taskProgressMap.remove(taskId);
    if (progress != null && progress.isChanged()) {
      manager.updateProgress(taskId, progress.report());
    }
  }

  public void delta(long taskId, long delta) {
    taskProgressMap.computeIfAbsent(taskId, t -> new Progress()).delta(delta);
  }

  @Override
  public void run() {
    Set<Long> taskIds = taskProgressMap.keySet();
    for (long taskId : taskIds) {
      Progress progress = taskProgressMap.get(taskId);
      if (progress == null) {
        continue;
      }

      if (progress.isChanged()) {
        // 任务有变动就更新到数据库中，这样前端可以看到任务进度的变动
        this.manager.updateProgress(taskId, progress.report());
      }
    }
  }

  static class Progress {

    /**
     * 使用java原子类控制进度，防止多线程修改进度出问题
     */
    private final LongAdder adder = new LongAdder();

    /**
     * 任务进度更新时间
     */
    private long updateTime;

    /**
     * 任务进度报告时间
     */
    private long reportTime;

    /**
     * 更新进度
     * @param delta
     */
    public void delta(long delta) {
      this.updateTime = System.currentTimeMillis();
      adder.add(delta);
    }

    /**
     * 报告当前进度
     * @return
     */
    public int report() {
      this.reportTime = System.currentTimeMillis();
      return adder.intValue();
    }

    /**
     * 判断任务进度是否有变动
     * @return
     */
    public boolean isChanged() {
      return reportTime < updateTime;
    }
  }
}
