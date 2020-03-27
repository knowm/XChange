package org.knowm.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public final class HuobiAllTicker {

  private final BigDecimal amount;
  private final long count;
  private final BigDecimal open;
  private final BigDecimal close;
  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal vol;
  private final HuobiPrice bid;
  private final HuobiPrice ask;
  private final String symbol;
  private Date ts;

  public HuobiAllTicker(
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("close") BigDecimal close,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("count") long count,
      @JsonProperty("vol") BigDecimal vol,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("bidSize") BigDecimal bidSize,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("askSize") BigDecimal askSize) {
    this.amount = amount;
    this.count = count;
    this.open = open;
    this.close = close;
    this.low = low;
    this.high = high;
    this.vol = vol;
    this.bid = new HuobiPrice(new BigDecimal[] {bid, bidSize});
    this.ask = new HuobiPrice(new BigDecimal[] {ask, askSize});
    this.symbol = symbol;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public long getCount() {
    return count;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getVol() {
    return vol;
  }

  public HuobiPrice getBid() {
    return bid;
  }

  public HuobiPrice getAsk() {
    return ask;
  }

  public Date getTs() {
    return ts;
  }

  public void setTs(Date ts) {
    this.ts = ts;
  }

  public String getSymbol() {
    return symbol;
  }

  @Override
  public String toString() {
    return "HuobiAllTicker [symbol="
        + getSymbol()
        + ", amount="
        + getAmount()
        + ", count="
        + getCount()
        + ", open="
        + getOpen()
        + ", close="
        + getClose()
        + ", low="
        + getLow()
        + ", high="
        + getHigh()
        + ", vol="
        + getVol()
        + ", bid="
        + getBid().toString()
        + ", ask="
        + getAsk().toString()
        + "]";
  }
}
