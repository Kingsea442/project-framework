package com.sea.api.exception;

import com.sea.api.resp.ApiResp;
import com.sea.api.resp.Status;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 拦截接口异常统一处理
 * Created by wanglh on 2020/6/17.
 */
@ControllerAdvice
public class ExceptionAdvice {

  @ResponseBody
  @ExceptionHandler(ApiException.class)
  @ResponseStatus(HttpStatus.OK)
  public ApiResp handleApiException(ApiException ex, HttpServletRequest httpServletRequest, Locale locale) {
    ApiError error = ex.getError();
    String errorMsg = ex.getTip();
    Status status = new Status(error.getCode(), error.getDescription(), errorMsg);
    return ApiResp.error(status);
  }

  @ResponseBody
  @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class, TypeMismatchException.class,
      HttpRequestMethodNotSupportedException.class, ServletRequestBindingException.class,
      NoHandlerFoundException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResp handleBadRequestException(BadRequestException ex, HttpServletRequest httpServletRequest, Locale locale) {
    String errorMsg = ex.getMessage();
    ApiError apiError = ApiError.BAD_REQUEST;
    Status status = new Status(apiError.getCode(), apiError.getDescription(), errorMsg);
    return ApiResp.error(status);
  }
}
