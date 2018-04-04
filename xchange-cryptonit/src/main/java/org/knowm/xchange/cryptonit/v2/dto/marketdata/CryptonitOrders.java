package org.knowm.xchange.cryptonit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.Map;

/** Data object representing a Trade from Cryptonit */
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
