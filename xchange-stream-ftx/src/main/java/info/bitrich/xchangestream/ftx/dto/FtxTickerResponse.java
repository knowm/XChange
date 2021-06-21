package info.bitrich.xchangestream.ftx.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;

public class FtxTickerResponse implements Serializable {

  @JsonProperty("time")
  private final Date time;

  @JsonProperty("bid")
  @JsonIgnore
  private final BigDecimal bid;

  @JsonProperty("bidSize")
  @JsonIgnore
  private final BigDecimal bidSize;

  @JsonProperty("ask")
  @JsonIgnore
  private final BigDecimal ask;

  @JsonProperty("askSize")
  @JsonIgnore
  private final BigDecimal askSize;

  @JsonProperty("last")
  @JsonIgnore
  private final BigDecimal last;

  public FtxTickerResponse(
      @JsonProperty("time") Double time,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("bidSize") BigDecimal bidSize,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("askSize") BigDecimal askSize,
      @JsonProperty("last") BigDecimal last) {
    this.time = Date.from(Instant.ofEpochMilli(((Double) (time * 1000)).longValue()));
    this.bid = bid;
    this.bidSize = bidSize;
    this.ask = ask;
    this.askSize = askSize;
    this.last = last;
  }

  public Date getTime() {
    return time;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getBidSize() {
    return bidSize;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getAskSize() {
    return askSize;
  }

  public BigDecimal getLast() {
    return last;
  }

  public Ticker toTicker(Instrument instrument) {
    return new Ticker.Builder()
        .instrument(instrument)
        .timestamp(getTime())
        .ask(getAsk())
        .askSize(getAskSize())
        .bid(getBid())
        .bidSize(getBidSize())
        .last(getLast())
        .build();
  }

  @Override
  public String toString() {
    return "FtxTickerResponse{"
        + "time="
        + time
        + ", bid="
        + bid
        + ", bidSize="
        + bidSize
        + ", ask="
        + ask
        + ", askSize="
        + askSize
        + ", last="
        + last
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FtxTickerResponse that = (FtxTickerResponse) o;
    return Objects.equals(getTime(), that.getTime())
        && Objects.equals(getBid(), that.getBid())
        && Objects.equals(getBidSize(), that.getBidSize())
        && Objects.equals(getAsk(), that.getAsk())
        && Objects.equals(getAskSize(), that.getAskSize())
        && Objects.equals(getLast(), that.getLast());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTime(), getBid(), getBidSize(), getAsk(), getAskSize(), getLast());
  }
}
