package info.bitrich.xchangestream.binance.futures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction;
import info.bitrich.xchangestream.binance.dto.ProductBinanceWebSocketTransaction;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.*;
import org.knowm.xchange.binance.futures.dto.trade.PositionSide;
import org.knowm.xchange.binance.futures.dto.trade.WorkingType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;

import java.math.BigDecimal;

public class OrderTradeUpdate {

  private final String symbol;
  private final String clientOrderId;
  private final OrderSide side;
  private final OrderType orderType;
  private final TimeInForce timeInForce;
  private final BigDecimal orderQuantity;
  private final BigDecimal orderPrice;
  private final BigDecimal averagePrice;
  private final BigDecimal stopPrice;
  private final ExecutionReportBinanceUserTransaction.ExecutionType executionType;
  private final OrderStatus currentOrderStatus;
  private final long orderId;
  private final BigDecimal lastExecutedQuantity;
  private final BigDecimal cumulativeFilledQuantity;
  private final BigDecimal lastExecutedPrice;
  private final BigDecimal commissionAmount;
  private final String commissionAsset;
  private final long timestamp;
  private final long tradeId;
  private final boolean buyerMarketMaker;
  private final BigDecimal bidNotional;
  private final BigDecimal askNotional;
  private final boolean reduceOnly;
  private final WorkingType stopPriceWorkingType;
  private final OrderType originalOrderType;
  private final PositionSide positionSide;
  private final boolean closeAll;
  private final BigDecimal activationPrice;
  private final BigDecimal callbackRate;
  private final BigDecimal realizedProfit;

  public OrderTradeUpdate(
          @JsonProperty("s") String symbol,
          @JsonProperty("c") String clientOrderId,
          @JsonProperty("S") String side,
          @JsonProperty("o") String orderType,
          @JsonProperty("f") String timeInForce,
          @JsonProperty("q") BigDecimal quantity,
          @JsonProperty("p") BigDecimal price,
          @JsonProperty("ap") BigDecimal averagePrice,
          @JsonProperty("sp") BigDecimal stopPrice,
          @JsonProperty("x") String currentExecutionType,
          @JsonProperty("X") String currentOrderStatus,
          @JsonProperty("i") long orderId,
          @JsonProperty("l") BigDecimal lastExecutedQuantity,
          @JsonProperty("z") BigDecimal cumulativeFilledQuantity,
          @JsonProperty("L") BigDecimal lastExecutedPrice,
          @JsonProperty("N") String commissionAsset,
          @JsonProperty("n") BigDecimal commissionAmount,
          @JsonProperty("T") long timestamp,
          @JsonProperty("t") long tradeId,
          @JsonProperty("b") BigDecimal bidNotional,
          @JsonProperty("a") BigDecimal askNotional,
          @JsonProperty("m") boolean buyerMarketMaker,
          @JsonProperty("R") boolean reduceOnly,
          @JsonProperty("wt") WorkingType stopPriceWorkingType,
          @JsonProperty("ot") OrderType originalOrderType,
          @JsonProperty("ps") PositionSide positionSide,
          @JsonProperty("cp") boolean closeAll,
          @JsonProperty("AP") BigDecimal activationPrice,
          @JsonProperty("cr") BigDecimal callbackRate,
          @JsonProperty("rp") BigDecimal realizedProfit) {
    this.symbol = symbol;
    this.clientOrderId = clientOrderId;
    this.side = OrderSide.valueOf(side);
    this.orderType = OrderType.valueOf(orderType);
    this.timeInForce = TimeInForce.valueOf(timeInForce);
    this.orderQuantity = quantity;
    this.orderPrice = price;
    this.averagePrice = averagePrice;
    this.stopPrice = stopPrice;
    this.executionType = ExecutionReportBinanceUserTransaction.ExecutionType.valueOf(currentExecutionType);
    this.currentOrderStatus = OrderStatus.valueOf(currentOrderStatus);
    this.orderId = orderId;
    this.lastExecutedQuantity = lastExecutedQuantity;
    this.cumulativeFilledQuantity = cumulativeFilledQuantity;
    this.lastExecutedPrice = lastExecutedPrice;
    this.commissionAmount = commissionAmount;
    this.commissionAsset = commissionAsset;
    this.timestamp = timestamp;
    this.tradeId = tradeId;
    this.bidNotional = bidNotional;
    this.askNotional = askNotional;
    this.buyerMarketMaker = buyerMarketMaker;
    this.reduceOnly = reduceOnly;
    this.stopPriceWorkingType = stopPriceWorkingType;
    this.originalOrderType = originalOrderType;
    this.positionSide = positionSide;
    this.closeAll = closeAll;
    this.activationPrice = activationPrice;
    this.callbackRate = callbackRate;
    this.realizedProfit = realizedProfit;
  }

  public String getSymbol() {
    return symbol;
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

  public BigDecimal getAveragePrice() {
    return averagePrice;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  public ExecutionReportBinanceUserTransaction.ExecutionType getExecutionType() {
    return executionType;
  }

  public OrderStatus getCurrentOrderStatus() {
    return currentOrderStatus;
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

  public boolean isBuyerMarketMaker() {
    return buyerMarketMaker;
  }

  public BigDecimal getBidNotional() {
    return bidNotional;
  }

  public BigDecimal getAskNotional() {
    return askNotional;
  }

  public boolean isReduceOnly() {
    return reduceOnly;
  }

  public WorkingType getStopPriceWorkingType() {
    return stopPriceWorkingType;
  }

  public OrderType getOriginalOrderType() {
    return originalOrderType;
  }

  public PositionSide getPositionSide() {
    return positionSide;
  }

  public boolean isCloseAll() {
    return closeAll;
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
}
