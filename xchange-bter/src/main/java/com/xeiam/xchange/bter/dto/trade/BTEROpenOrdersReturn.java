package com.xeiam.xchange.bter.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by David Henry on 2/19/14.
 */
public class BTEROpenOrdersReturn {

  private final Boolean result;
  private final List<BTEROpenOrderSummary> orders;
  private final String msg;

  /**
   * Constructor
   *
   * @param result
   * @param orders
   * @param msg
   */
  public BTEROpenOrdersReturn(@JsonProperty("result") Boolean result, @JsonProperty("orders") List<BTEROpenOrderSummary> orders,
                              @JsonProperty("msg") String msg) {
    this.result = result;
    this.orders = orders;
    this.msg = msg;
  }

  public Boolean isResult() {
    return result;
  }

  public List<BTEROpenOrderSummary> getOrders() {
    return orders;
  }

  public String getMsg() {
    return msg;
  }

  @Override
  public String toString() {
    return "BTEROpenOrdersReturn{" +
            "result=" + result +
            ", orders=" + orders +
            ", msg='" + msg + '\'' +
            '}';
  }

}
