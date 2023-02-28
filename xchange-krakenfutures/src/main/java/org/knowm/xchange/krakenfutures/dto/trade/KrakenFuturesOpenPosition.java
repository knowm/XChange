package org.knowm.xchange.krakenfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

/** @author Panchen */
@Getter
@ToString
public class KrakenFuturesOpenPosition extends KrakenFuturesResult {

  private final Date fillTime;
  private final String symbol;
  private final String side;
  private final BigDecimal size;
  private final BigDecimal price;

  public KrakenFuturesOpenPosition(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("fillTime") Date fillTime,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") String side,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("price") BigDecimal price) {

    super(result, error);

    this.fillTime = fillTime;
    this.symbol = symbol;
    this.side = side;
    this.size = size;
    this.price = price;
  }
}
