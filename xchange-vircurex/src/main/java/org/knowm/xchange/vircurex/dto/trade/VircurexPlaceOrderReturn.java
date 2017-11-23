package org.knowm.xchange.vircurex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VircurexPlaceOrderReturn {

  private final String orderId;

  public String getOrderId() {

    return orderId;
  }

  /**
   * Constructor
   *
   * @param orderId
   */
  public VircurexPlaceOrderReturn(@JsonProperty("orederid") String orderId) {

    this.orderId = orderId;
  }

}
