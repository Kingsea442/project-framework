package com.sea.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Created by wanglh on 2020/6/30.
 */
public class RequestUtil {

  public static String resolveRequestId() {
    return StringUtils.defaultString(resolveRequestId());
  }

  public static Object resolveRequestAttribute(String key) {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes == null) {
      return null;
    }

    return requestAttributes.getAttribute(key, RequestAttributes.SCOPE_REQUEST);
  }
}
