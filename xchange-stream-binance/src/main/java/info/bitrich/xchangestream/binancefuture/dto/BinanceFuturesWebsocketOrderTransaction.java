package info.bitrich.xchangestream.binancefuture.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction.ExecutionType;
import java.math.BigDecimal;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;

public class BinanceFuturesWebsocketOrderTransaction {

  private final String symbol;
  private final String clientOrderId;
  private final String commissionAsset;
  private final String stopPriceWorkingType;
  private final String positionSide;
  private final long orderId;
  private final TimeInForce timeInForce;
  private final OrderSide side;
  private final OrderType orderType;
  private final OrderType originalOrderType;
  private final OrderStatus orderStatus;
  private final ExecutionType executionType;
  private final BigDecimal originalQuantity;
  private final BigDecimal originalPrice;
  private final BigDecimal averagePrice;
  private final BigDecimal stopPrice;
  private final BigDecimal orderLastFilledQuantity;
  private final BigDecimal orderFilledAccumulatedQuantity;
  private final BigDecimal lastFilledPrice;
  private final BigDecimal commissionAmount;
  private final BigDecimal bidNotional;
  private final BigDecimal askNotional;
  private final BigDecimal activationPrice;
  private final BigDecimal callbackRate;
  private final BigDecimal realizedProfit;
  private final Boolean isCloseAll;
  private final Long orderTradeTime;
  private final Long tradeId;
  private final Boolean isMakerSide;
  private final Boolean isReduceOnly;

  BinanceFuturesWebsocketOrderTransaction(
      @JsonProperty("s") String symbol,
      @JsonProperty("c") String clientOrderId,
      @JsonProperty("S") String side,
      @JsonProperty("o") String orderType,
      @JsonProperty("f") String timeInForce,
      @JsonProperty("q") BigDecimal originalQuantity,
      @JsonProperty("p") BigDecimal originalPrice,
      @JsonProperty("ap") BigDecimal averagePrice,
      @JsonProperty("sp") BigDecimal stopPrice,
      @JsonProperty("x") String executionType,
      @JsonProperty("X") String orderStatus,
      @JsonProperty("i") long orderId,
      @JsonProperty("l") BigDecimal orderLastFilledQuantity,
      @JsonProperty("z") BigDecimal orderFilledAccumulatedQuantity,
      @JsonProperty("L") BigDecimal lastFilledPrice,
      @JsonProperty("N") String commissionAsset,
      @JsonProperty("n") BigDecimal commissionAmount,
      @JsonProperty("T") Long orderTradeTime,
      @JsonProperty("t") Long tradeId,
      @JsonProperty("b") BigDecimal bidNotional,
      @JsonProperty("a") BigDecimal askNotional,
      @JsonProperty("m") Boolean isMakerSide,
      @JsonProperty("R") Boolean isReduceOnly,
      @JsonProperty("wt") String stopPriceWorkingType,
      @JsonProperty("ot") String originalOrderType,
      @JsonProperty("ps") String positionSide,
      @JsonProperty("cp") Boolean isCloseAll,
      @JsonProperty("AP") BigDecimal activationPrice,
      @JsonProperty("cr") BigDecimal callbackRate,
      @JsonProperty("rp") BigDecimal realizedProfit
  ) {
    this.symbol = symbol;
    this.clientOrderId = clientOrderId;
    this.originalQuantity = originalQuantity;
    this.originalPrice = originalPrice;
    this.averagePrice = averagePrice;
    this.stopPrice = stopPrice;
    this.timeInForce = TimeInForce.valueOf(timeInForce);
    this.side = OrderSide.valueOf(side);
    this.orderType = OrderType.valueOf(orderType);
    this.originalOrderType = OrderType.valueOf(originalOrderType);
    this.executionType = ExecutionType.valueOf(executionType);
    this.orderStatus = OrderStatus.valueOf(orderStatus);
    this.orderId = orderId;
    this.orderLastFilledQuantity = orderLastFilledQuantity;
    this.orderFilledAccumulatedQuantity = orderFilledAccumulatedQuantity;
    this.lastFilledPrice = lastFilledPrice;
    this.commissionAmount = commissionAmount;
    this.bidNotional = bidNotional;
    this.askNotional = askNotional;
    this.activationPrice = activationPrice;
    this.callbackRate = callbackRate;
    this.realizedProfit = realizedProfit;
    this.commissionAsset = commissionAsset;
    this.stopPriceWorkingType = stopPriceWorkingType;
    this.positionSide = positionSide;
    this.isCloseAll = isCloseAll;
    this.orderTradeTime = orderTradeTime;
    this.tradeId = tradeId;
    this.isMakerSide = isMakerSide;
    this.isReduceOnly = isReduceOnly;
  }

  public String getSymbol() {
    return symbol;
  }

  public TimeInForce getTimeInForce() {
    return timeInForce;
  }

  public OrderSide getSide() {
    return side;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public OrderType getOriginalOrderType() {
    return originalOrderType;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public ExecutionType getExecutionType() {
    return executionType;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public long getOrderId() {
    return orderId;
  }

  public BigDecimal getOriginalQuantity() {
    return originalQuantity;
  }

  public BigDecimal getOriginalPrice() {
    return originalPrice;
  }

  public BigDecimal getAveragePrice() {
    return averagePrice;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  public String getCommissionAsset() {
    return commissionAsset;
  }

  public String getStopPriceWorkingType() {
    return stopPriceWorkingType;
  }

  public String getPositionSide() {
    return positionSide;
  }

  public BigDecimal getOrderLastFilledQuantity() {
    return orderLastFilledQuantity;
  }

  public BigDecimal getOrderFilledAccumulatedQuantity() {
    return orderFilledAccumulatedQuantity;
  }

  public BigDecimal getLastFilledPrice() {
    return lastFilledPrice;
  }

  public BigDecimal getCommissionAmount() {
    return commissionAmount;
  }

  public BigDecimal getBidNotional() {
    return bidNotional;
  }

  public BigDecimal getAskNotional() {
    return askNotional;
  }

  public BigDecimal getActivationPrice() {
    return activationPrice;
  }

  public BigDecimal getCallbackRate() {
    return callbackRate;
  }

  public BigDecimal getRealizedProfit() {
    return realizedProfit;
  }

  public Boolean getCloseAll() {
    return isCloseAll;
  }

  public Long getOrderTradeTime() {
    return orderTradeTime;
  }

  public Long getTradeId() {
    return tradeId;
  }

  public Boolean getMakerSide() {
    return isMakerSide;
  }

  public Boolean getReduceOnly() {
    return isReduceOnly;
  }
}
