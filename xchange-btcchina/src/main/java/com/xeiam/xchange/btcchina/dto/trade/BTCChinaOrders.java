package com.xeiam.xchange.btcchina.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaOrders {

  private final List<BTCChinaOrder> orders;
  private final List<BTCChinaOrder> btcCnyOrders;
  private final List<BTCChinaOrder> ltcCnyOrders;
  private final List<BTCChinaOrder> ltcBtcOrders;

  /**
   * @param orders
   * @param btcCnyOrders
   * @param ltcCnyOrders
   * @param ltcBtcOrders
   */
  public BTCChinaOrders(@JsonProperty("order") List<BTCChinaOrder> orders, @JsonProperty("order_btccny") List<BTCChinaOrder> btcCnyOrders,
      @JsonProperty("order_ltccny") List<BTCChinaOrder> ltcCnyOrders, @JsonProperty("order_ltcbtc") List<BTCChinaOrder> ltcBtcOrders) {

    this.orders = orders;
    this.btcCnyOrders = btcCnyOrders;
    this.ltcCnyOrders = ltcCnyOrders;
    this.ltcBtcOrders = ltcBtcOrders;
  }

  public List<BTCChinaOrder> getOrders() {

    return orders;
  }

  public List<BTCChinaOrder> getBtcCnyOrders() {

    return btcCnyOrders;
  }

  public List<BTCChinaOrder> getLtcCnyOrders() {

    return ltcCnyOrders;
  }

  public List<BTCChinaOrder> getLtcBtcOrders() {

    return ltcBtcOrders;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaOrders{orders=%s, btcCnyOrders=%s, ltcCnyorders=%s, ltcBtcOrders=%s}", orders, btcCnyOrders, ltcCnyOrders, ltcBtcOrders);
  }

}
