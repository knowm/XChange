package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class GeminiOrderStatusResponse {

  private final long id;
  private final String clientOrderId;
  private final String exchange;
  private final String symbol;
  private final BigDecimal price;
  private final BigDecimal avgExecutionPrice;
  private final String side;
  private final String type;
  private final String timestamp;
  private final long timestampms;
  private final boolean isLive;
  private final boolean isCancelled;
  private final boolean wasForced;
  private final BigDecimal originalAmount;
  private final BigDecimal remainingAmount;
  private final BigDecimal executedAmount;
  private final OrderStatusTradeDetails[] trades;

  /**
   * Constructor
   *
   * @param id
   * @param symbol
   * @param price
   * @param avgExecutionPrice
   * @param side
   * @param type
   * @param timestamp
   * @param isLive
   * @param isCancelled
   * @param wasForced
   * @param originalAmount
   * @param remainingAmount
   * @param executedAmount
   * @param trades
   */
  public GeminiOrderStatusResponse(
      @JsonProperty("order_id") long id,
      @JsonProperty("client_order_id") String clientOrderId,
      @JsonProperty("exchange") String exchange,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("avg_execution_price") BigDecimal avgExecutionPrice,
      @JsonProperty("side") String side,
      @JsonProperty("type") String type,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("timestampms") long timestampms,
      @JsonProperty("is_live") boolean isLive,
      @JsonProperty("is_cancelled") boolean isCancelled,
      @JsonProperty("was_forced") boolean wasForced,
      @JsonProperty("original_amount") BigDecimal originalAmount,
      @JsonProperty("remaining_amount") BigDecimal remainingAmount,
      @JsonProperty("executed_amount") BigDecimal executedAmount,
      @JsonProperty("trades") OrderStatusTradeDetails[] trades) {
    this.id = id;
    this.clientOrderId = clientOrderId;
    this.exchange = exchange;
    this.symbol = symbol;
    this.price = price;
    this.avgExecutionPrice = avgExecutionPrice;
    this.side = side;
    this.type = type;
    this.timestamp = timestamp;
    this.timestampms = timestampms;
    this.isLive = isLive;
    this.isCancelled = isCancelled;
    this.wasForced = wasForced;
    this.originalAmount = originalAmount;
    this.remainingAmount = remainingAmount;
    this.executedAmount = executedAmount;
    this.trades = trades;
  }

  public OrderStatusTradeDetails[] getTrades() {
    return this.trades;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("GeminiOrderStatusResponse [id=");
    builder.append(id);
    builder.append(", symbol=");
    builder.append(symbol);
    builder.append(", price=");
    builder.append(price);
    builder.append(", avgExecutionPrice=");
    builder.append(avgExecutionPrice);
    builder.append(", side=");
    builder.append(side);
    builder.append(", type=");
    builder.append(type);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", isLive=");
    builder.append(isLive);
    builder.append(", isCancelled=");
    builder.append(isCancelled);
    builder.append(", wasForced=");
    builder.append(wasForced);
    builder.append(", originalAmount=");
    builder.append(originalAmount);
    builder.append(", remainingAmount=");
    builder.append(remainingAmount);
    builder.append(", executedAmount=");
    builder.append(executedAmount);
    builder.append("]");
    return builder.toString();
  }

  @Getter
  public static class OrderStatusTradeDetails {
    private BigDecimal price;
    private BigDecimal amount;
    private String timestamp;
    private String timestampms;
    private String type;
    private boolean aggressor;
    private String feeCurrency;
    private BigDecimal feeAmount;
    private long tradeId;
    private String orderId;
    private String clientOrderId;
    private String exchange;
    private boolean isAuctionFill;
    private String Break;

    public OrderStatusTradeDetails(
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("timestamp") String timestamp,
        @JsonProperty("timestampms") String timestampms,
        @JsonProperty("type") String type,
        @JsonProperty("aggressor") boolean aggressor,
        @JsonProperty("fee_currency") String feeCurrency,
        @JsonProperty("fee_amount") String feeAmount,
        @JsonProperty("tid") long tradeId,
        @JsonProperty("order_id") String orderId,
        @JsonProperty("client_order_id") String clientOrderId,
        @JsonProperty("exchange") String exchange,
        @JsonProperty("is_auction_fill") boolean isAuctionFill,
        @JsonProperty("break") String Break) {
      this.price = price;
      this.amount = amount;
      this.timestamp = timestamp;
      this.timestampms = timestampms;
      this.type = type;
      this.aggressor = aggressor;
      this.feeCurrency = feeCurrency;
      this.feeAmount = new BigDecimal(feeAmount);
      this.tradeId = tradeId;
      this.orderId = orderId;
      this.clientOrderId = clientOrderId;
      this.exchange = exchange;
      this.isAuctionFill = isAuctionFill;
      this.Break = Break;
    }
  }
}
