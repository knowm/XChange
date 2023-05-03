package org.knowm.xchange.krakenfutures.dto.marketData;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesOrderSide;

/** @author Panchen */
@Getter
@ToString
public class KrakenFuturesPublicFill extends KrakenFuturesResult {

  private final Date time;
  private final String tradeId;
  private final BigDecimal price;
  private final BigDecimal size;
  private final KrakenFuturesOrderSide side;
  private final String type;

  public KrakenFuturesPublicFill(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("time") Date time,
      @JsonProperty("trade_id") String tradeId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("side") KrakenFuturesOrderSide side,
      @JsonProperty("type") String type) {

    super(result, error);

    this.time = time;
    this.tradeId = tradeId;
    this.price = price;
    this.size = size;
    this.side = side;
    this.type = type;
  }
}
