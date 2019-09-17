package org.knowm.xchange.binance.dto.account.margin;

import java.math.BigDecimal;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import org.knowm.xchange.currency.CurrencyPair;

public class ForceLiquidationRec {

  private BigDecimal avgPrice;
  private BigDecimal executedQty;
  private Long orderId;
  private BigDecimal price;
  private BigDecimal qty;
  private OrderSide side;
  private CurrencyPair symbol;
  private TimeInForce timeInForce;
  private Long updatedTime;

  public ForceLiquidationRec(
      BigDecimal avgPrice,
      BigDecimal executedQty,
      Long orderId,
      BigDecimal price,
      BigDecimal qty,
      String side,
      String symbol,
      String timeInForce,
      Long updatedTime) {
    this.avgPrice = avgPrice;
    this.executedQty = executedQty;
    this.orderId = orderId;
    this.price = price;
    this.qty = qty;
    this.side = OrderSide.valueOf(side);
    this.symbol = BinanceAdapters.adaptSymbol(symbol);
    this.timeInForce = TimeInForce.valueOf(timeInForce);
    this.updatedTime = updatedTime;
  }

  public BigDecimal getAvgPrice() {
    return avgPrice;
  }

  public void setAvgPrice(BigDecimal avgPrice) {
    this.avgPrice = avgPrice;
  }

  public BigDecimal getExecutedQty() {
    return executedQty;
  }

  public void setExecutedQty(BigDecimal executedQty) {
    this.executedQty = executedQty;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getQty() {
    return qty;
  }

  public void setQty(BigDecimal qty) {
    this.qty = qty;
  }

  public OrderSide getSide() {
    return side;
  }

  public void setSide(OrderSide side) {
    this.side = side;
  }

  public CurrencyPair getSymbol() {
    return symbol;
  }

  public void setSymbol(CurrencyPair symbol) {
    this.symbol = symbol;
  }

  public TimeInForce getTimeInForce() {
    return timeInForce;
  }

  public void setTimeInForce(TimeInForce timeInForce) {
    this.timeInForce = timeInForce;
  }

  public Long getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(Long updatedTime) {
    this.updatedTime = updatedTime;
  }
}
