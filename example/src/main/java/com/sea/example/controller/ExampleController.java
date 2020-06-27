package com.sea.example.controller;

import com.google.common.collect.Lists;
import com.sea.api.exception.ApiError;
import com.sea.api.exception.ApiException;
import com.sea.api.exception.BadRequestException;
import com.sea.api.resp.ApiResp;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wanglh on 2020/6/17.
 */
@RestController
@RequestMapping(value = "/api/example/v1")
public class ExampleController {

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