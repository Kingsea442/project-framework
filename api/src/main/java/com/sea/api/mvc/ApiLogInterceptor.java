package com.sea.api.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 拦截api请求，输出api log
 * Created by wanglh on 2020/6/28.
 */
@Slf4j
public class ApiLogInterceptor extends HandlerInterceptorAdapter implements InitializingBean {
  // 需要在项目log4j2.xml中配置log.api的logger
  private static final Logger apiLogger = LoggerFactory.getLogger("log.api");
  private static final String KEY_API_HANDLE_START = ApiLogInterceptor.class.getName() + ".start";
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void afterPropertiesSet() throws Exception {
    Preconditions.checkNotNull(objectMapper, "no ObjectMapper");
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    request.setAttribute(KEY_API_HANDLE_START, System.currentTimeMillis());
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    try {
      ObjectNode logNode = objectMapper.createObjectNode();
      final long cost = System.currentTimeMillis() - (long) request.getAttribute(KEY_API_HANDLE_START);
      logNode.put("cost", cost);
      logNode.put("status", response.getStatus());
      if (handler instanceof HandlerMethod) {
        logNode.put("handler", ((HandlerMethod) handler).getMethod().toGenericString());
      }

//      if (ex != null || request.getAttribute())

      logNode.put("url", request.getRequestURL().toString());

      apiLogger.info(objectMapper.writeValueAsString(logNode));
    } catch (Throwable t) {
      log.error("Fail to log api, url: " + request.getRequestURL().toString(), t);
    }
  }
}
