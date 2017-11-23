package org.knowm.xchange.cryptonit.v2.dto.marketdata;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * <p>
 * Data object representing a Trade from Cryptonit
 * </p>
 */

public final class CryptonitOrders {

  private final Map<String, CryptonitOrder> orders = new HashMap<>();

  public Map<String, CryptonitOrder> getOrders() {

    return orders;
  }

  public CryptonitOrder getOrder(long orderId) {

    return orders.get(String.valueOf(orderId));
  }

  @JsonAnySetter
  public void addOrder(String tid, CryptonitOrder order) {

    this.orders.put(tid, order);
  }

}
