package org.knowm.xchange.coinsetter.dto.order.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A list of orders.
 */
public class CoinsetterOrderList {

  private final CoinsetterOrder[] orderList;

  public CoinsetterOrderList(@JsonProperty("orderList") CoinsetterOrder[] orderList) {

    this.orderList = orderList;
  }

  public CoinsetterOrder[] getOrderList() {

    return orderList;
  }

}
