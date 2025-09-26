package info.bitrich.xchangestream.coinsph.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

/**
 * WebSocket execution report message from Coins.ph. Example:
 * {"e":"executionReport","E":1598464861000,"s":"BTCUSDT","c":"myOrder1","S":"BUY","o":"LIMIT","f":"GTC","q":"1.0","p":"9000","X":"FILLED"}
 */
@Getter
@ToString
public class CoinsphWebSocketExecutionReport {
  @JsonProperty("e")
  private String eventType;

  @JsonProperty("E")
  private long eventTime;

  @JsonProperty("s")
  private String symbol;

  @JsonProperty("c")
  private String clientOrderId;

  @JsonProperty("S")
  private String side;

  @JsonProperty("o")
  private String orderType;

  @JsonProperty("f")
  private String timeInForce;

  @JsonProperty("q")
  private BigDecimal quantity;

  @JsonProperty("p")
  private BigDecimal price;

  @JsonProperty("X")
  private String orderStatus;

  @JsonProperty("i")
  private long orderId;

  @JsonProperty("z")
  private BigDecimal cumulativeFilledQuantity;

  @JsonProperty("L")
  private BigDecimal lastExecutedPrice;

  @JsonProperty("l")
  private BigDecimal lastExecutedQuantity;

  @JsonProperty("n")
  private BigDecimal commission;

  @JsonProperty("N")
  private String commissionAsset;

  @JsonProperty("T")
  private long tradeTime;

  @JsonProperty("t")
  private long tradeId;

  @JsonProperty("m")
  private boolean isMarketMaker;

  @JsonProperty("x")
  private String executionType;

  public CoinsphWebSocketExecutionReport(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") long eventTime,
      @JsonProperty("s") String symbol,
      @JsonProperty("c") String clientOrderId,
      @JsonProperty("S") String side,
      @JsonProperty("o") String orderType,
      @JsonProperty("f") String timeInForce,
      @JsonProperty("q") BigDecimal quantity,
      @JsonProperty("p") BigDecimal price,
      @JsonProperty("X") String orderStatus,
      @JsonProperty("i") long orderId,
      @JsonProperty("z") BigDecimal cumulativeFilledQuantity,
      @JsonProperty("L") BigDecimal lastExecutedPrice,
      @JsonProperty("l") BigDecimal lastExecutedQuantity,
      @JsonProperty("n") BigDecimal commission,
      @JsonProperty("N") String commissionAsset,
      @JsonProperty("T") long tradeTime,
      @JsonProperty("t") long tradeId,
      @JsonProperty("m") boolean isMarketMaker,
      @JsonProperty("x") String executionType) {
    this.eventType = eventType;
    this.eventTime = eventTime;
    this.symbol = symbol;
    this.clientOrderId = clientOrderId;
    this.side = side;
    this.orderType = orderType;
    this.timeInForce = timeInForce;
    this.quantity = quantity;
    this.price = price;
    this.orderStatus = orderStatus;
    this.orderId = orderId;
    this.cumulativeFilledQuantity = cumulativeFilledQuantity;
    this.lastExecutedPrice = lastExecutedPrice;
    this.lastExecutedQuantity = lastExecutedQuantity;
    this.commission = commission;
    this.commissionAsset = commissionAsset;
    this.tradeTime = tradeTime;
    this.tradeId = tradeId;
    this.isMarketMaker = isMarketMaker;
    this.executionType = executionType;
  }
}
