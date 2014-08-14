package com.xeiam.xchange.btcchina.dto.trade;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

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

  /**
   * @deprecated Use {@link #getOrdersArray()} instead.
   */
  @Deprecated
  public List<BTCChinaOrder> getOrders() {

    return orders == null ? null : Arrays.asList(orders);
  }

  public BTCChinaOrder[] getOrdersArray() {

    return orders;
  }

  /**
   * @deprecated Use {@link #get(Object)} instead.
   */
  @Deprecated
  public List<BTCChinaOrder> getBtcCnyOrders() {

    return Arrays.asList(get("order_btccny"));
  }

  /**
   * @deprecated Use {@link #get(Object)} instead.
   */
  @Deprecated
  public List<BTCChinaOrder> getLtcCnyOrders() {

    return Arrays.asList(get("order_ltccny"));
  }

  /**
   * @deprecated Use {@link #get(Object)} instead.
   */
  @Deprecated
  public List<BTCChinaOrder> getLtcBtcOrders() {

    return Arrays.asList(get("order_ltcbtc"));
  }

  public long getDate() {

    return date;
  }

  @Override
  public String toString() {

    return ToStringBuilder.reflectionToString(this);
  }

}
