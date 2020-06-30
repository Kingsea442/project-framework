package com.sea.common.helper;

import java.io.Serializable;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

/**
 * DEMO 自定义日志appender
 * Created by wanglh on 2020/6/30.
 */
@Plugin(name = MyLoggerAppender.PLUGIN_NAME, category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class MyLoggerAppender extends AbstractAppender {
  public static final String PLUGIN_NAME = "Sea";

  protected MyLoggerAppender(String name, Filter filter,
      Layout<? extends Serializable> layout,
      boolean ignoreExceptions,
      Property[] properties) {
    super(name, filter, layout, ignoreExceptions, properties);
  }

  @Override
  public void append(LogEvent event) {
    System.out.println("do what you want to do here");
  }

  @PluginFactory
  public static MyLoggerAppender createAppender(@PluginAttribute("name") String name,
      @PluginElement("Filter") final Filter filter,
      @PluginElement("Layout") Layout<String> layout,
      @PluginAttribute("ignoreExceptions") boolean ignoreExceptions) {
    return new MyLoggerAppender(name, filter, layout, ignoreExceptions, null);
  }
}
