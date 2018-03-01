package org.knowm.xchange.bibox.dto;

public class BiboxError {
  private int code;
  private String msg;

  public BiboxError() {
    // TODO Default Constructor for Jackson
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
