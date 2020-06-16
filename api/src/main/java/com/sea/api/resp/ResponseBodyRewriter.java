package com.sea.api.resp;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by wanglh on 2020/6/16.
 */
@ControllerAdvice(annotations = Controller.class)
public class ResponseBodyRewriter implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    // todo 暂时拦截全部，后面可根据需求来决定要拦截哪些
    return true;
  }

  @SuppressWarnings({"unchecked", "rawtype"})
  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {
    if (body instanceof ApiResp) {
      // 防止重复rewrite
      return body;
    }

    return ApiResp.ok(body);
  }
}
