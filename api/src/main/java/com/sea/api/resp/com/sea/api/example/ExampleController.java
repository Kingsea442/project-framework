package com.sea.api.resp.com.sea.api.example;

import com.google.common.collect.Lists;
import com.sea.api.resp.ApiResp;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wanglh on 2020/6/16.
 */
@RestController
@RequestMapping(value = "/api/example")
public class ExampleController {

  @GetMapping(value = "/response/body/rewrite")
  public List<String> testResponseBodyRewrite() {
    return Lists.newArrayList("hello", "world");
  }
}
