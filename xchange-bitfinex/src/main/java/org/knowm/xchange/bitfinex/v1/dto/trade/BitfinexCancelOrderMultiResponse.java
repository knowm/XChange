package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexCancelOrderMultiResponse {

  private final String result;

  /**
   * Constructor
   *
   * @param result
   */
  public BitfinexCancelOrderMultiResponse(@JsonProperty("result") String result) {

    this.result = result;
  }

  public String getResult() {
    return result;
  }

  @Override
  public String toString() {
    return "BitfinexCancelOrderMultiResponse [result=" + result + "]";
  }

}
