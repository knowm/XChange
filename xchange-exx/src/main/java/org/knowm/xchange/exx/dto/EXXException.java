package org.knowm.xchange.exx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EXXException extends RuntimeException {

  /** */
  private static final long serialVersionUID = 1L;

  public final int code;
  public final String msg;

  public EXXException(@JsonProperty("code") int code, @JsonProperty("msg") String msg) {
    super(code + ": " + msg);
    this.code = code;
    this.msg = msg;
  }
}
