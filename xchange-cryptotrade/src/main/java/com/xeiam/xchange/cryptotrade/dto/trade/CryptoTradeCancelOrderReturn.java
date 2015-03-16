package com.xeiam.xchange.cryptotrade.dto.trade;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;

public class CryptoTradeCancelOrderReturn extends CryptoTradeBaseResponse {

  private final CryptoTradeCancelledOrderData cancelledOrderData;

  private CryptoTradeCancelOrderReturn(@JsonProperty("data") CryptoTradeCancelledOrderData placedOrderData, @JsonProperty("status") String status,
      @JsonProperty("error") String error) {

    super(status, error);
    this.cancelledOrderData = placedOrderData;
  }

  public int getOrderId() {

    return cancelledOrderData.getOrderId();
  }

  public String getOrderStatus() {

    return cancelledOrderData.getOrderStatus();
  }

  public Map<String, BigDecimal> getFunds() {

    return cancelledOrderData.getFunds();
  }

  @Override
  public String toString() {

    return "CryptoTradeCancelOrderReturn [cancelledOrderData=" + cancelledOrderData + "]";
  }

  private static class CryptoTradeCancelledOrderData {

    private final int orderId;
    private final String orderStatus;
    private final Map<String, BigDecimal> funds;

    private CryptoTradeCancelledOrderData(@JsonProperty("order_id") int orderId, @JsonProperty("orderstatus") String orderStatus,
        @JsonProperty("funds") Map<String, BigDecimal> funds) {

      this.orderStatus = orderStatus;
      this.orderId = orderId;
      this.funds = funds;
    }

    public int getOrderId() {

      return orderId;
    }

    public String getOrderStatus() {

      return orderStatus;
    }

    public Map<String, BigDecimal> getFunds() {

      return funds;
    }

    @Override
    public String toString() {

      return "CryptoTradeCancelledOrderData [orderId=" + orderId + ", orderStatus=" + orderStatus + ", funds=" + funds + "]";
    }
  }
}
