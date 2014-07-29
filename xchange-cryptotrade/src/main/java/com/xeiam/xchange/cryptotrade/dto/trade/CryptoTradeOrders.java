package com.xeiam.xchange.cryptotrade.dto.trade;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.jackson.CurrencyPairDeserializer;

public class CryptoTradeOrders extends CryptoTradeBaseResponse {

  private final List<CryptoTradeOrder> orders;

  private CryptoTradeOrders(@JsonProperty("data") List<CryptoTradeOrder> orders, @JsonProperty("status") String status, @JsonProperty("error") String error) {

    super(status, error);
    this.orders = orders;
  }

  public List<CryptoTradeOrder> getOrders() {

    return orders;
  }

  @Override
  public String toString() {

    return "CryptoTradeOrders [orders=" + orders + "]";
  }

  public static class CryptoTradeOrder {

    private final long id;
    private final long timestamp;
    private final CurrencyPair currencyPair;
    private final CryptoTradeOrderType type;
    private final BigDecimal initialAmount;
    private final BigDecimal remainingAmount;
    private final BigDecimal rate;
    private final String status;

    private CryptoTradeOrder(@JsonProperty("id") long id, @JsonProperty("timestamp") long timestamp,
        @JsonProperty("pair") @JsonDeserialize(using = CurrencyPairDeserializer.class) CurrencyPair currencyPair, @JsonProperty("type") CryptoTradeOrderType type,
        @JsonProperty("initial_amount") BigDecimal initialAmount, @JsonProperty("remaining_amount") BigDecimal remainingAmount, @JsonProperty("rate") BigDecimal rate,
        @JsonProperty("status") String status) {

      this.id = id;
      this.timestamp = timestamp;
      this.currencyPair = currencyPair;
      this.type = type;
      this.initialAmount = initialAmount;
      this.remainingAmount = remainingAmount;
      this.rate = rate;
      this.status = status;
    }

    public long getId() {

      return id;
    }

    public long getTimestamp() {

      return timestamp;
    }

    public CurrencyPair getCurrencyPair() {

      return currencyPair;
    }

    public CryptoTradeOrderType getType() {

      return type;
    }

    public BigDecimal getInitialAmount() {

      return initialAmount;
    }

    public BigDecimal getRemainingAmount() {

      return remainingAmount;
    }

    public BigDecimal getRate() {

      return rate;
    }

    public String getStatus() {

      return status;
    }

    @Override
    public String toString() {

      return "CryptoTradeOrder [id=" + id + ", timestamp=" + timestamp + ", currencyPair=" + currencyPair + ", type=" + type + ", initialAmount=" + initialAmount + ", remainingAmount="
          + remainingAmount + ", rate=" + rate + ", status=" + status + "]";
    }

  }
}
