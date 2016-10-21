package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiCancelOrderMultiResponse {

  private final String result;

  /**
   * Constructor
   * 
   * @param result
   */
  public GeminiCancelOrderMultiResponse(@JsonProperty("result") String result) {

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
