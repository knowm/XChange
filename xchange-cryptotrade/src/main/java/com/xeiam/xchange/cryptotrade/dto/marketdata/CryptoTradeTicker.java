package com.xeiam.xchange.cryptotrade.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeTickerDeserializer;

@JsonDeserialize(using = CryptoTradeTickerDeserializer.class)
public class CryptoTradeTicker extends CryptoTradeBaseResponse {

  private final BigDecimal last;
  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal volumeTradeCurrency;
  private final BigDecimal volumePriceCurrency;
  private final BigDecimal minAsk;
  private final BigDecimal maxBid;

  public CryptoTradeTicker(BigDecimal last, BigDecimal low, BigDecimal high, BigDecimal volumeTradeCurrency, BigDecimal volumePriceCurrency,
      BigDecimal minAsk, BigDecimal maxBid, String status, String error) {

    super(status, error);
    this.last = last;
    this.low = low;
    this.high = high;
    this.volumeTradeCurrency = volumeTradeCurrency;
    this.volumePriceCurrency = volumePriceCurrency;
    this.minAsk = minAsk;
    this.maxBid = maxBid;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getVolumeTradeCurrency() {

    return volumeTradeCurrency;
  }

  public BigDecimal getVolumePriceCurrency() {

    return volumePriceCurrency;
  }

  public BigDecimal getMinAsk() {

    return minAsk;
  }

  public BigDecimal getMaxBid() {

    return maxBid;
  }

  @Override
  public String toString() {

    return "CryptoTradeTicker [last=" + last + ", low=" + low + ", high=" + high + ", volumeTradeCurrency=" + volumeTradeCurrency
        + ", volumePriceCurrency=" + volumePriceCurrency + ", minAsk=" + minAsk + ", maxBid=" + maxBid + "]";
  }

}
