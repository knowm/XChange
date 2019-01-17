package org.knowm.xchange.dragonex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DragonResult<T> {

  public final boolean ok;
  public final int code;
  public final String msg;
  private final T data;

  public DragonResult(
      @JsonProperty("ok") boolean ok,
      @JsonProperty("code") int code,
      @JsonProperty("msg") String msg,
      @JsonProperty("data") T data) {
    this.ok = ok;
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public T getResult() {
    if (!ok) {
      throw new DragonexException(msg + ", code: " + code);
    }
    return data;
  }

  @Override
  public String toString() {
    return "DragonResult [ok="
        + ok
        + ", code="
        + code
        + ", "
        + (msg != null ? "msg=" + msg + ", " : "")
        + (data != null ? "data=" + data : "")
        + "]";
  }
}
