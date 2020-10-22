package com.ndong.courseweb.dto;

import javax.persistence.Column;
import java.io.Serializable;

public class MediaTypeDTO extends AbstractDTO implements Serializable {

  private String name;

  private String code;

  private String extension;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }
}
