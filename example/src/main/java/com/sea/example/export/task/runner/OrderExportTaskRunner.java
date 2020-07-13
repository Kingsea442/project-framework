package com.sea.example.export.task.runner;

import com.sea.export.TaskRunner;
import com.sea.export.model.ExportTask;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * demo 导出框架使用
 * Created by wanglh on 2020/7/13.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrderExportTaskRunner extends TaskRunner {
  public OrderExportTaskRunner() {
  }

  public OrderExportTaskRunner(ExportTask task) {
    setTask(task);
  }

  @Override
  protected void parseConfig() {
    // todo 这里可以根据业务定义一些导出参数，比如导出时间段，这里省略

  }

  @Override
  protected int countAll() {
    // todo 根据业务返回订单总条数
    return 1000;
  }

  @Override
  protected void doExport() throws IOException {
    // todo 这里实现业务数据组装逻辑，假如1000条数据
    for (int i = 0; i < 1000; i++) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      manager.deltaProgress(task.getId(), 1);
    }

    // todo 数据组装完之后保存到本地文件中 比如
    FileUtils.write(tmpFile, "write biz data in this file", StandardCharsets.UTF_8);
  }
}
