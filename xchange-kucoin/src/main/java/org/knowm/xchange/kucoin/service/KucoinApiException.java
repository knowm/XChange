/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.service;

/** Based on code by zicong.lu on 2018/12/14. */
public class KucoinApiException extends RuntimeException {

  private static final long serialVersionUID = 8592824166988095909L;

  private String code;

  public KucoinApiException(String message) {
    super(message);
  }

  public KucoinApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public KucoinApiException(String code, String message) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return "KucoinApiException{"
        + "code='"
        + getCode()
        + '\''
        + ", message='"
        + getMessage()
        + '\''
        + '}';
  }
}
