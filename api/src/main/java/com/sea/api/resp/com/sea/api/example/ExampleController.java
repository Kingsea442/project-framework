package com.sea.api.resp.com.sea.api.example;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wanglh on 2020/6/16.
 */
@RestController
@RequestMapping(value = "/api/example")
@Slf4j
public class ExampleController {

  @GetMapping(value = "/response/body/rewrite")
  public List<String> testResponseBodyRewrite() {
    log.info("first slf4j log test :{}", "hello world");
    return Lists.newArrayList("hello", "world");
  }
}
