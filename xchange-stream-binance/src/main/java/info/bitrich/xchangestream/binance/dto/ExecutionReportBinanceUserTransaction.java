package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;

public class ExecutionReportBinanceUserTransaction extends ProductBinanceWebSocketTransaction {

  public enum ExecutionType {
    NEW,
    CANCELED,
    REPLACED,
    REJECTED,
    TRADE,
    EXPIRED
  }

  private final String clientOrderId;
  private final OrderSide side;
  private final OrderType orderType;
  private final TimeInForce timeInForce;
  private final BigDecimal orderQuantity;
  private final BigDecimal orderPrice;
  private final BigDecimal stopPrice;
  private final BigDecimal icebergQuantity;
  private final ExecutionType executionType;
  private final OrderStatus currentOrderStatus;
  private final String orderRejectReason;
  private final long orderId;
  private final BigDecimal lastExecutedQuantity;
  private final BigDecimal cumulativeFilledQuantity;
  private final BigDecimal lastExecutedPrice;
  private final BigDecimal commissionAmount;
  private final String commissionAsset;
  private final long timestamp;
  private final long tradeId;
  private final boolean working;
  private final boolean buyerMarketMaker;
  private final BigDecimal cumulativeQuoteAssetTransactedQuantity;

  public ExecutionReportBinanceUserTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("s") String symbol,
      @JsonProperty("c") String clientOrderId,
      @JsonProperty("S") String side,
      @JsonProperty("o") String orderType,
      @JsonProperty("f") String timeInForce,
      @JsonProperty("q") BigDecimal quantity,
      @JsonProperty("p") BigDecimal price,
      @JsonProperty("P") BigDecimal stopPrice,
      @JsonProperty("F") BigDecimal icebergQuantity,
      @JsonProperty("x") String currentExecutionType,
      @JsonProperty("X") String currentOrderStatus,
      @JsonProperty("r") String orderRejectReason,
      @JsonProperty("i") long orderId,
      @JsonProperty("l") BigDecimal lastExecutedQuantity,
      @JsonProperty("z") BigDecimal cumulativeFilledQuantity,
      @JsonProperty("L") BigDecimal lastExecutedPrice,
      @JsonProperty("n") BigDecimal commissionAmount,
      @JsonProperty("N") String commissionAsset,
      @JsonProperty("T") long timestamp,
      @JsonProperty("t") long tradeId,
      @JsonProperty("w") boolean working,
      @JsonProperty("m") boolean buyerMarketMaker,
      @JsonProperty("Z") BigDecimal cumulativeQuoteAssetTransactedQuantity) {
    super(eventType, eventTime, symbol);
    this.clientOrderId = clientOrderId;
    this.side = OrderSide.valueOf(side);
    this.orderType = OrderType.valueOf(orderType);
    this.timeInForce = TimeInForce.valueOf(timeInForce);
    this.orderQuantity = quantity;
    this.orderPrice = price;
    this.stopPrice = stopPrice;
    this.icebergQuantity = icebergQuantity;
    this.executionType = ExecutionType.valueOf(currentExecutionType);
    this.currentOrderStatus = OrderStatus.valueOf(currentOrderStatus);
    this.orderRejectReason = orderRejectReason;
    this.orderId = orderId;
    this.lastExecutedQuantity = lastExecutedQuantity;
    this.cumulativeFilledQuantity = cumulativeFilledQuantity;
    this.lastExecutedPrice = lastExecutedPrice;
    this.commissionAmount = commissionAmount;
    this.commissionAsset = commissionAsset;
    this.timestamp = timestamp;
    this.tradeId = tradeId;
    this.working = working;
    this.buyerMarketMaker = buyerMarketMaker;
    this.cumulativeQuoteAssetTransactedQuantity = cumulativeQuoteAssetTransactedQuantity;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public OrderSide getSide() {
    return side;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public TimeInForce getTimeInForce() {
    return timeInForce;
  }

  public BigDecimal getOrderQuantity() {
    return orderQuantity;
  }

  public BigDecimal getOrderPrice() {
    return orderPrice;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  public BigDecimal getIcebergQuantity() {
    return icebergQuantity;
  }

  public ExecutionType getExecutionType() {
    return executionType;
  }

  public OrderStatus getCurrentOrderStatus() {
    return currentOrderStatus;
  }

  public String getOrderRejectReason() {
    return orderRejectReason;
  }

  public long getOrderId() {
    return orderId;
  }

  public BigDecimal getLastExecutedQuantity() {
    return lastExecutedQuantity;
  }

  public BigDecimal getCumulativeFilledQuantity() {
    return cumulativeFilledQuantity;
  }

  public BigDecimal getLastExecutedPrice() {
    return lastExecutedPrice;
  }

  public BigDecimal getCommissionAmount() {
    return commissionAmount;
  }

  public String getCommissionAsset() {
    return commissionAsset;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public long getTradeId() {
    return tradeId;
  }

  public boolean isWorking() {
    return working;
  }

  public boolean isBuyerMarketMaker() {
    return buyerMarketMaker;
  }

  public BigDecimal getCumulativeQuoteAssetTransactedQuantity() {
    return cumulativeQuoteAssetTransactedQuantity;
  }

  public UserTrade toUserTrade() {
    if (executionType != ExecutionType.TRADE) throw new IllegalStateException("Not a trade");
    return new UserTrade.Builder()
        .type(BinanceAdapters.convert(side))
        .originalAmount(lastExecutedQuantity)
        .currencyPair(currencyPair)
        .price(lastExecutedPrice)
        .timestamp(getEventTime())
        .id(Long.toString(tradeId))
        .orderId(Long.toString(orderId))
        .feeAmount(commissionAmount)
        .feeCurrency(Currency.getInstance(commissionAsset))
        .build();
  }

  public Order toOrder() {
    return BinanceAdapters.adaptOrder(
        new BinanceOrder(
            BinanceAdapters.toSymbol(getCurrencyPair()),
            orderId,
            clientOrderId,
            orderPrice,
            orderQuantity,
            lastExecutedQuantity,
            cumulativeFilledQuantity,
            currentOrderStatus,
            timeInForce,
            orderType,
            side,
            stopPrice,
            BigDecimal.ZERO,
            timestamp));
  }

  @Override
  public String toString() {
    return "ExecutionReportBinanceUserTransaction [eventTime="
        + getEventTime()
        + ", currencyPair="
        + getCurrencyPair()
        + ", clientOrderId="
        + clientOrderId
        + ", side="
        + side
        + ", orderType="
        + orderType
        + ", timeInForce="
        + timeInForce
        + ", quantity="
        + orderQuantity
        + ", price="
        + orderPrice
        + ", stopPrice="
        + stopPrice
        + ", icebergQuantity="
        + icebergQuantity
        + ", executionType="
        + executionType
        + ", currentOrderStatus="
        + currentOrderStatus
        + ", orderRejectReason="
        + orderRejectReason
        + ", orderId="
        + orderId
        + ", lastExecutedQuantity="
        + lastExecutedQuantity
        + ", cumulativeFilledQuantity="
        + cumulativeFilledQuantity
        + ", lastExecutedPrice="
        + lastExecutedPrice
        + ", commissionAmount="
        + commissionAmount
        + ", commissionAsset="
        + commissionAsset
        + ", timestamp="
        + timestamp
        + ", tradeId="
        + tradeId
        + ", working="
        + working
        + ", buyerMarketMaker="
        + buyerMarketMaker
        + ", cumulativeQuoteAssetTransactedQuantity="
        + cumulativeQuoteAssetTransactedQuantity
        + "]";
  }
}
