package org.knowm.xchange.bter.dto.trade;

import java.util.List;

import org.knowm.xchange.bter.dto.BTERBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by David Henry on 2/19/14.
 */
public class BTEROpenOrders extends BTERBaseResponse {

  private final List<BTEROpenOrder> orders;

  /**
   * Constructor
   *
   * @param result
   * @param orders
   * @param msg
   */
  public BTEROpenOrders(@JsonProperty("result") Boolean result, @JsonProperty("orders") List<BTEROpenOrder> orders, @JsonProperty("msg") String msg) {

    super(result, msg);
    this.orders = orders;
  }

  public List<BTEROpenOrder> getOrders() {

    return orders;
  }

  @Override
  public String toString() {

    return "BTEROpenOrdersReturn [orders=" + orders + "]";
  }

}
