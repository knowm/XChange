package org.knowm.xchange.coinex.dto;

import lombok.Data;
import si.mazi.rescu.ExceptionalReturnContentException;

@Data
public class CoinexResponse<T> {

  private Integer code;

  private T data;

  private String message;

  public void setCode(Integer code) {
    if (code != 0) {
      throw new ExceptionalReturnContentException(String.valueOf(code));
    }
    this.code = code;
  }

}
