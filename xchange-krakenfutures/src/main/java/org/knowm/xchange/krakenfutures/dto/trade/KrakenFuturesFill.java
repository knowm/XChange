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
public class KrakenFuturesFill extends KrakenFuturesResult {

  private final Date fillTime;
  private final String orderId;
  private final String fillId;
  private final String cliOrdId;
  private final String symbol;
  private final KrakenFuturesOrderSide side;
  private final BigDecimal size;
  private final BigDecimal price;
  private final String fillType;

  public KrakenFuturesFill(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("fillTime") Date fillTime,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("fill_id") String fillId,
      @JsonProperty("cliOrdId") String cliOrdId,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") KrakenFuturesOrderSide side,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("fillType") String fillType) {

    super(result, error);

    this.fillTime = fillTime;
    this.orderId = orderId;
    this.fillId = fillId;
    this.cliOrdId = cliOrdId;
    this.symbol = symbol;
    this.side = side;
    this.size = size;
    this.price = price;
    this.fillType = fillType;
  }
}
