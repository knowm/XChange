package com.xeiam.xchange.cryptotrade.dto.trade;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;

public class CryptoTradePlaceOrderReturn extends CryptoTradeBaseResponse {

  private final CryptoTradePlacedOrderData placedOrderData;

  private CryptoTradePlaceOrderReturn(@JsonProperty("data") CryptoTradePlacedOrderData placedOrderData, @JsonProperty("status") String status,
      @JsonProperty("error") String error) {

    super(status, error);
    this.placedOrderData = placedOrderData;
  }

  public BigDecimal getBought() {

    return placedOrderData.getBought();
  }

  public BigDecimal getSold() {

    return placedOrderData.getSold();
  }

  public BigDecimal getRemaining() {

    return placedOrderData.getRemaining();
  }

  public int getOrderId() {

    return placedOrderData.getOrderId();
  }

  public Map<String, BigDecimal> getFunds() {

    return placedOrderData.getFunds();
  }

  @Override
  public String toString() {

    return "CryptoTradePlaceOrderReturn [placedOrderData=" + placedOrderData + "]";
  }

  private static class CryptoTradePlacedOrderData {

    private final BigDecimal bought;
    private final BigDecimal sold;
    private final BigDecimal remaining;
    private final int orderId;
    private final Map<String, BigDecimal> funds;

    private CryptoTradePlacedOrderData(@JsonProperty("Bought") BigDecimal bought, @JsonProperty("Sold") BigDecimal sold,
        @JsonProperty("remaining") BigDecimal remaining, @JsonProperty("order_id") int orderId, @JsonProperty("funds") Map<String, BigDecimal> funds) {

      this.bought = bought;
      this.sold = sold;
      this.remaining = remaining;
      this.orderId = orderId;
      this.funds = funds;
    }

    public BigDecimal getBought() {

      return bought;
    }

    public BigDecimal getSold() {

      return sold;
    }

    public BigDecimal getRemaining() {

      return remaining;
    }

    public int getOrderId() {

      return orderId;
    }

    public Map<String, BigDecimal> getFunds() {

      return funds;
    }

    @Override
    public String toString() {

      return "CryptoTradePlacedOrderData [bought=" + bought + ", sold=" + sold + ", remaining=" + remaining + ", orderId=" + orderId + ", funds="
          + funds + "]";
    }

  }
}
