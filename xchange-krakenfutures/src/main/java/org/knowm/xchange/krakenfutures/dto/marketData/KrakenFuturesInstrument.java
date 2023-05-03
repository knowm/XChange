package org.knowm.xchange.krakenfutures.dto.marketData;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

/** @author Neil Panchen */
@ToString
@Getter
public class KrakenFuturesInstrument extends KrakenFuturesResult {

  private final boolean tradeable;
  private final Date lastTradingTime;
  private final String symbol;
  private final String underlying;
  private final BigDecimal contractSize;
  private final String type;
  private final BigDecimal tickSize;
  private final BigDecimal minimumTradeSize;
  private final BigDecimal impactMidSize;
  private final Integer volumeScale;

  public KrakenFuturesInstrument(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("tradeable") boolean tradeable,
      @JsonProperty("lastTradingTime") Date lastTradingTime,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("underlying") String underlying,
      @JsonProperty("contractSize") BigDecimal contractSize,
      @JsonProperty("type") String type,
      @JsonProperty("tickSize") BigDecimal tickSize,
      @JsonProperty("minimumTradeSize") BigDecimal minimumTradeSize,
      @JsonProperty("impactMidSize") BigDecimal impactMidSize,
      @JsonProperty("contractValueTradePrecision") Integer volumeScale) {

    super(result, error);

    this.tradeable = tradeable;
    this.lastTradingTime = lastTradingTime;
    this.symbol = symbol;
    this.underlying = underlying;
    this.contractSize = contractSize;
    this.type = type;
    this.tickSize = tickSize;
    this.minimumTradeSize = minimumTradeSize;
    this.impactMidSize = impactMidSize;
    this.volumeScale = volumeScale;
  }
}
