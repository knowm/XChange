package com.xeiam.xchange.cryptotrade.dto.trade;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.jackson.CurrencyPairDeserializer;

public class CryptoTradeTrades extends CryptoTradeBaseResponse {

  private final List<CryptoTradeTrade> trades;

  private CryptoTradeTrades(@JsonProperty("data") List<CryptoTradeTrade> trades, @JsonProperty("status") String status,
      @JsonProperty("error") String error) {

    super(status, error);
    this.trades = trades;
  }

  public List<CryptoTradeTrade> getTrades() {

    return trades;
  }

  @Override
  public String toString() {

    return "CryptoTradeTrades [trades=" + trades + "]";
  }

  public static class CryptoTradeTrade {

    private final long id;
    private final long timestamp;
    private final CurrencyPair currencyPair;
    private final CryptoTradeOrderType type;
    private final BigDecimal amount;
    private final BigDecimal rate;
    private final long myOrder;

    private CryptoTradeTrade(@JsonProperty("id") long id, @JsonProperty("timestamp") long timestamp, @JsonProperty("pair") @JsonDeserialize(
        using = CurrencyPairDeserializer.class) CurrencyPair currencyPair, @JsonProperty("type") CryptoTradeOrderType type,
        @JsonProperty("amount") BigDecimal amount, @JsonProperty("rate") BigDecimal rate, @JsonProperty("my_order") long myOrder) {

      this.id = id;
      this.timestamp = timestamp;
      this.currencyPair = currencyPair;
      this.type = type;
      this.amount = amount;
      this.rate = rate;
      this.myOrder = myOrder;
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

    public BigDecimal getAmount() {

      return amount;
    }

    public BigDecimal getRate() {

      return rate;
    }

    public long getMyOrder() {

      return myOrder;
    }

    @Override
    public String toString() {

      return "CryptoTradeTrade [id=" + id + ", timestamp=" + timestamp + ", currencyPair=" + currencyPair + ", type=" + type + ", amount=" + amount
          + ", rate=" + rate + ", myOrder=" + myOrder + "]";
    }

  }
}
