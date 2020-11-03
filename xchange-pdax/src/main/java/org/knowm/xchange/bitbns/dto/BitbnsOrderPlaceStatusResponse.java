package org.knowm.xchange.bitbns.dto;

public class BitbnsOrderPlaceStatusResponse {

  private String data;
  private int status;
  private String error;
  private long id;
  private int code;
  private String msg;

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  @Override
  public String toString() {
    return "BitbnsOrderPlaceStatusResponse [data="
        + data
        + ", status="
        + status
        + ", error="
        + error
        + ", id="
        + id
        + ", code="
        + code
        + ", msg="
        + msg
        + "]";
  }
}
