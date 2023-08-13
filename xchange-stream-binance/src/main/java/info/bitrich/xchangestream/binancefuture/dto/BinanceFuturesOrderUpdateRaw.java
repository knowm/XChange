package info.bitrich.xchangestream.binancefuture.dto;

import static info.bitrich.xchangestream.binancefuture.BinanceFuturesAdapters.guessContract;

import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction.ExecutionType;
import java.math.BigDecimal;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.derivative.FuturesContract;

public class BinanceFuturesOrderUpdateRaw {

  private final FuturesContract contract;
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

  public BinanceFuturesOrderUpdateRaw(BinanceFuturesOrderUpdateTransaction orderUpdate) {
    BinanceFuturesWebsocketOrderTransaction order = orderUpdate.getOrder();
    this.contract = guessContract(order.getSymbol());
    this.clientOrderId = order.getClientOrderId();
    this.originalQuantity = order.getOriginalQuantity();
    this.originalPrice = order.getOriginalPrice();
    this.averagePrice = order.getAveragePrice();
    this.stopPrice = order.getStopPrice();
    this.timeInForce = order.getTimeInForce();
    this.side = order.getSide();
    this.orderType = order.getOrderType();
    this.originalOrderType = order.getOrderType();
    this.executionType = order.getExecutionType();
    this.orderStatus = order.getOrderStatus();
    this.orderId = order.getOrderId();
    this.orderLastFilledQuantity = order.getOrderLastFilledQuantity();
    this.orderFilledAccumulatedQuantity = order.getOrderFilledAccumulatedQuantity();
    this.lastFilledPrice = order.getLastFilledPrice();
    this.commissionAmount = order.getCommissionAmount();
    this.bidNotional = order.getBidNotional();
    this.askNotional = order.getAskNotional();
    this.activationPrice = order.getActivationPrice();
    this.callbackRate = order.getCallbackRate();
    this.realizedProfit = order.getRealizedProfit();
    this.commissionAsset = order.getCommissionAsset();
    this.stopPriceWorkingType = order.getStopPriceWorkingType();
    this.positionSide = order.getPositionSide();
    this.isCloseAll = order.getCloseAll();
    this.orderTradeTime = order.getOrderTradeTime();
    this.tradeId = order.getTradeId();
    this.isMakerSide = order.getMakerSide();
    this.isReduceOnly = order.getReduceOnly();
  }


  public FuturesContract getContract() {
    return contract;
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

  @Override
  public String toString() {
    return "BinanceFuturesOrderUpdateRaw [contract=" + contract + ", clientOrderId=" + clientOrderId
        + ", commissionAsset=" + commissionAsset + ", stopPriceWorkingType=" + stopPriceWorkingType
        + ", positionSide=" + positionSide + ", orderId=" + orderId + ", timeInForce="
        + timeInForce + ", side=" + side + ", orderType=" + orderType + ", originalOrderType="
        + originalOrderType + ", orderStatus=" + orderStatus + ", executionType=" + executionType
        + ", originalQuantity=" + originalQuantity + ", originalPrice=" + originalPrice
        + ", averagePrice=" + averagePrice + ", stopPrice=" + stopPrice
        + ", orderLastFilledQuantity=" + orderLastFilledQuantity
        + ", orderFilledAccumulatedQuantity=" + orderFilledAccumulatedQuantity
        + ", lastFilledPrice=" + lastFilledPrice + ", commissionAmount=" + commissionAmount
        + ", bidNotional=" + bidNotional + ", askNotional=" + askNotional + ", activationPrice="
        + activationPrice + ", callbackRate=" + callbackRate + ", realizedProfit=" + realizedProfit
        + ", isCloseAll=" + isCloseAll + ", orderTradeTime=" + orderTradeTime + ", tradeId="
        + tradeId + ", isMakerSide=" + isMakerSide + ", isReduceOnly=" + isReduceOnly + "]";
  }
}
