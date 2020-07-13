package com.sea.export;

import com.google.common.base.Preconditions;
import com.sea.export.model.Attachment;
import com.sea.export.model.ExportTask;
import java.io.File;
import java.io.IOException;

/**
 * 任务执行器，封装任务执行步骤
 * Created by wanglh on 2020/7/13.
 */
public abstract class TaskRunner implements Runnable{

  protected TaskManager manager;
  protected ExportTask task;
  // 临时保存到本地的文件
  protected File tmpFile;

  public TaskRunner() {
  }

  protected TaskRunner setTask(ExportTask exportTask) {
    Preconditions.checkNotNull(exportTask);
    this.task = exportTask;
    return this;
  }

  public void setManager(TaskManager manager) {
    this.manager = manager;
    this.tmpFile = manager.tmpFile(task);
  }

  public ExportTask getTask() {
    return this.task;
  }

  @Override
  public void run() {
    try {
      task = manager.started(task);
      parseConfig();
      int total = countAll();
      task = manager.inProgress(task, total);
      doExport();
      Attachment attachment = upload();
      task = manager.finish(task, attachment.getResPath());
    } catch (Throwable t) {
      task = manager.error(task, t);
      t.printStackTrace();
    } finally {
      manager.remove(task.getId());
    }

  }

  protected abstract void parseConfig();

  protected abstract int countAll();

  protected abstract void doExport() throws IOException;


  /**
   * 上传存储服务器，返回url供用户下载，比如上传到oss
   * @return
   * @throws IOException
   */
  protected Attachment upload() throws IOException{
    // todo uplooad to oss
    Attachment attachment = new Attachment();
    // 用户拿到这个连接直接下载就行了
    attachment.setDownloadUrl("http://");
    attachment.setResPath("oss://ssss.sss.ss");
    return attachment;
  }
}
