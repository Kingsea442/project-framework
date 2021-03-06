package com.sea.example.controller;

import com.google.common.collect.Lists;
import com.sea.api.exception.ApiError;
import com.sea.api.exception.ApiException;
import com.sea.api.exception.BadRequestException;
import com.sea.api.resp.ApiResp;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wanglh on 2020/6/17.
 */
@RestController
@RequestMapping(value = "/api/example/v1")
@Slf4j
public class ExampleController {
  private static final Logger seaLogger = LoggerFactory.getLogger("custom.sea");

  @RequestMapping(value = "/log/test")
  public ApiResp log() {
    log.info("log test info");
    log.error("log test error");
    log.warn("log test warn");
    log.trace("log test trace");
    System.out.println("test log output");
    seaLogger.info("hello seaaaaaa");

    return ApiResp.ok(true);
  }

  @GetMapping(value = "/resp/rewrite")
  public List<String> apiRespExample() {
    return Lists.newArrayList("hello", "world");
  }

  @GetMapping(value = "/exception/test")
  public String testException() {
    throw new ApiException(ApiError.BAD_REQUEST, "test exception tip");
  }

  @GetMapping(value = "/exception/bad/request")
  public String testBadException() {
    throw new BadRequestException("test bad request exception");
  }
}
