package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;

public class GateioTicker extends GateioBaseResponse {
  private BigDecimal highestBid;

  private boolean result;

  private BigDecimal low24hr;

  private BigDecimal last;

  private BigDecimal high24hr;

  private BigDecimal percentChange;

  private BigDecimal lowestAsk;

  private BigDecimal quoteVolume;

  private BigDecimal baseVolume;

  public GateioTicker(
      @JsonProperty("result") boolean result,
      @JsonProperty("message") String message,
      @JsonProperty("highestBid") BigDecimal highestBid,
      @JsonProperty("low24hr") BigDecimal low24hr,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("high24hr") BigDecimal high24hr,
      @JsonProperty("percentChange") BigDecimal percentChange,
      @JsonProperty("lowestAsk") BigDecimal lowestAsk,
      @JsonProperty("quoteVolume") BigDecimal quoteVolume,
      @JsonProperty("baseVolume") BigDecimal baseVolume) {
    super(result, message);
    this.highestBid = highestBid;
    this.result = result;
    this.low24hr = low24hr;
    this.last = last;
    this.high24hr = high24hr;
    this.percentChange = percentChange;
    this.lowestAsk = lowestAsk;
    this.quoteVolume = quoteVolume;
    this.baseVolume = baseVolume;
  }

  public BigDecimal getHighestBid() {
    return highestBid;
  }

  @Override
  public boolean isResult() {
    return result;
  }

  public BigDecimal getLow24hr() {
    return low24hr;
  }

  public BigDecimal getLast() {
    return last;
  }

  public BigDecimal getHigh24hr() {
    return high24hr;
  }

  public BigDecimal getPercentChange() {
    return percentChange;
  }

  public BigDecimal getLowestAsk() {
    return lowestAsk;
  }

  public BigDecimal getQuoteVolume() {
    return quoteVolume;
  }

  public BigDecimal getBaseVolume() {
    return baseVolume;
  }

  @Override
  public String toString() {
    return "GateioTicker{"
        + "highestBid='"
        + highestBid
        + '\''
        + ", result='"
        + result
        + '\''
        + ", low24hr='"
        + low24hr
        + '\''
        + ", last='"
        + last
        + '\''
        + ", high24hr='"
        + high24hr
        + '\''
        + ", percentChange='"
        + percentChange
        + '\''
        + ", lowestAsk='"
        + lowestAsk
        + '\''
        + ", quoteVolume='"
        + quoteVolume
        + '\''
        + ", baseVolume='"
        + baseVolume
        + '\''
        + '}';
  }
}
