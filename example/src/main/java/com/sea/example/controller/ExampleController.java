package com.sea.example.controller;

import com.google.common.collect.Lists;
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
}
