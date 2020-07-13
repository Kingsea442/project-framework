package com.sea.export.model;

import lombok.Data;

/**
 * Created by wanglh on 2020/7/13.
 */
@Data
public class Attachment {
  private String md5;
  private String resPath;
  private long size;
  private String contentType;
  private String filename;
  private String downloadUrl ;
  private long modifyTime ;
}
