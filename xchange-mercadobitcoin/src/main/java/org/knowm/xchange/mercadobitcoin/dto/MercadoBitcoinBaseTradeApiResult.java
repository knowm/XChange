package org.knowm.xchange.mercadobitcoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinBaseTradeApiResult<R> {

  private final Integer success;
  private final String error;
  private final R theReturn;

  public MercadoBitcoinBaseTradeApiResult(
      @JsonProperty("success") Integer success,
      @JsonProperty("error") String error,
      @JsonProperty("return") R theReturn) {

    this.success = success;
    this.error = error;
    this.theReturn = theReturn;
  }

  public Integer getSuccess() {

    return success;
  }

  public String getError() {

    return error;
  }

  public R getTheReturn() {

    return theReturn;
  }

  @Override
  public String toString() {

    return "MercadoBitcoinBaseTradeApiResult ["
        + "success="
        + success
        + ", error='"
        + error
        + '\''
        + ", return="
        + theReturn
        + ']';
  }
}
