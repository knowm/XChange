package org.knowm.xchange.btcchina.dto.trade;

import java.util.Arrays;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author David Yam
 */
public class BTCChinaOrders extends LinkedHashMap<String, BTCChinaOrder[]> {

  private static final long serialVersionUID = 2014080301L;

  private final BTCChinaOrder[] orders;
  private final long date;

  /**
   * @param orders
   */
  public BTCChinaOrders(@JsonProperty("order") BTCChinaOrder[] orders, @JsonProperty("date") long date) {

    this.orders = orders;
    this.date = date;
  }

  public BTCChinaOrder[] getOrdersArray() {

    return orders;
  }

  public long getDate() {

    return date;
  }

  @Override
  public String toString() {

    return "BTCChinaOrders [orders=" + Arrays.toString(orders) + ", date=" + date + "]";
  }

}
