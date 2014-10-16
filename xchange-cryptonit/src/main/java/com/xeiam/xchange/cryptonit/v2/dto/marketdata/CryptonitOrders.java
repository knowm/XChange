package com.xeiam.xchange.cryptonit.v2.dto.marketdata;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * <p>
 * Data object representing a Trade from Cryptonit
 * </p>
 */

public final class CryptonitOrders {

  private final Map<String, CryptonitOrder> orders = new HashMap<String, CryptonitOrder>();

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

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("CryptonitOrders{ \n");

        for (Map.Entry<String, CryptonitOrder> entry : orders.entrySet()) {
            builder.append("   " + entry.getKey() + ": " + entry.getValue().toString() + "\n");
        }
        builder.append('}');
        return builder.toString();
    }
}
