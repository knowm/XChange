package org.knowm.xchange.krakenfutures.dto.marketData;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

/** @author Jean-Christophe Laruelle */
@Getter
@ToString
public class KrakenFuturesTicker extends KrakenFuturesResult {

  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal last;
  private final BigDecimal askSize;
  private final String symbol;
  private final Date lastTime;
  private final BigDecimal low24H;
  private final BigDecimal bidSize;
  private final boolean suspended;
  private final BigDecimal open24H;
  private final BigDecimal high24H;
  private final BigDecimal markPrice;
  private final BigDecimal lastSize;
  private final BigDecimal vol24H;
  private final String tag;
  private final String pair;
  private final BigDecimal absoluteFundingRate;
  private final BigDecimal absoluteFundingRatePrediction;

  public KrakenFuturesTicker(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("askSize") BigDecimal askSize,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("lastTime") Date lastTime,
      @JsonProperty("low24h") BigDecimal low24H,
      @JsonProperty("bidSize") BigDecimal bidSize,
      @JsonProperty("suspended") boolean suspended,
      @JsonProperty("open24h") BigDecimal open24H,
      @JsonProperty("high24h") BigDecimal high24H,
      @JsonProperty("markPrice") BigDecimal markPrice,
      @JsonProperty("lastSize") BigDecimal lastSize,
      @JsonProperty("vol24h") BigDecimal vol24H,
      @JsonProperty("tag") String tag,
      @JsonProperty("pair") String pair,
      @JsonProperty("fundingRate") BigDecimal absoluteFundingRate,
      @JsonProperty("fundingRatePrediction") BigDecimal absoluteFundingRatePrediction) {

    super(result, error);

    this.bid = bid;
    this.ask = ask;
    this.last = last;
    this.askSize = askSize;
    this.symbol = symbol;
    this.lastTime = lastTime;
    this.low24H = low24H;
    this.bidSize = bidSize;
    this.suspended = suspended;
    this.open24H = open24H;
    this.high24H = high24H;
    this.markPrice = markPrice;
    this.lastSize = lastSize;
    this.vol24H = vol24H;
    this.tag = tag;
    this.pair = pair;
    this.absoluteFundingRate = absoluteFundingRate;
    this.absoluteFundingRatePrediction = absoluteFundingRatePrediction;
  }
}
