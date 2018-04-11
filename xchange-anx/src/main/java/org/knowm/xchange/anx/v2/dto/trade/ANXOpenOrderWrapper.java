package org.knowm.xchange.anx.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author timmolter */
public class ANXOpenOrderWrapper {

  private final String result;
  private final ANXOpenOrder[] anxOpenOrders;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param anxOpenOrders
   * @param error
   */
  public ANXOpenOrderWrapper(
      @JsonProperty("result") String result,
      @JsonProperty("data") ANXOpenOrder[] anxOpenOrders,
      @JsonProperty("error") String error) {

    this.result = result;
    this.anxOpenOrders = anxOpenOrders;
    this.error = error;
  }

  public String getResult() {

    return result;
  }

  public ANXOpenOrder[] getANXOpenOrders() {

    return anxOpenOrders;
  }

  public String getError() {

    return error;
  }
}
