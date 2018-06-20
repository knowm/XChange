package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexTicker {

  private String symbol;
  private BigDecimal bid;
  private BigDecimal bidSize;
  private BigDecimal ask;
  private BigDecimal askSize;
  private BigDecimal dailyChange;
  private BigDecimal dailyChangePerc;
  private BigDecimal lastPrice;
  private BigDecimal volume;
  private BigDecimal high;
  private BigDecimal low;

  public BitfinexTicker() {}

  public BitfinexTicker(
      String symbol,
      BigDecimal bid,
      BigDecimal bidSize,
      BigDecimal ask,
      BigDecimal askSize,
      BigDecimal dailyChange,
      BigDecimal dailyChangePerc,
      BigDecimal lastPrice,
      BigDecimal volume,
      BigDecimal high,
      BigDecimal low) {

    this.symbol = symbol;
    this.bid = bid;
    this.bidSize = bidSize;
    this.ask = ask;
    this.askSize = askSize;
    this.dailyChange = dailyChange;
    this.dailyChangePerc = dailyChangePerc;
    this.lastPrice = lastPrice;
    this.volume = volume;
    this.high = high;
    this.low = low;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getBidSize() {
    return bidSize;
  }

  public BigDecimal getAskSize() {
    return askSize;
  }

  public BigDecimal getDailyChange() {
    return dailyChange;
  }

  public BigDecimal getDailyChangePerc() {
    return dailyChangePerc;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  @Override
  public String toString() {
    return "BitfinexTicker{"
        + "symbol='"
        + symbol
        + '\''
        + ", bid="
        + bid
        + ", bidSize="
        + bidSize
        + ", askSize="
        + askSize
        + ", dailyChange="
        + dailyChange
        + ", dailyChangePerc="
        + dailyChangePerc
        + ", lastPrice="
        + lastPrice
        + ", volume="
        + volume
        + ", high="
        + high
        + ", low="
        + low
        + '}';
  }
}
