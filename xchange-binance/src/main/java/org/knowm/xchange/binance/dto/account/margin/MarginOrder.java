package org.knowm.xchange.binance.dto.account.margin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;

public class MarginOrder {

  private String clientOrderId;
  private BigDecimal cummulativeQuoteQty;
  private BigDecimal executedQty;
  private BigDecimal icebergQty;
  private Boolean isWorking;
  private Long orderId;
  private BigDecimal origQty;
  private BigDecimal price;
  private OrderSide side;
  private OrderStatus status;
  private BigDecimal stopPrice;
  private CurrencyPair symbol;
  private Long time;
  private Long transactTime;
  private TimeInForce timeInForce;
  private OrderType type;
  private Long updateTime;

  public MarginOrder(
          @JsonProperty("clientOrderId") String clientOrderId,
          @JsonProperty("cummulativeQuoteQty") BigDecimal cummulativeQuoteQty,
          @JsonProperty("executedQty") BigDecimal executedQty,
          @JsonProperty("icebergQty") BigDecimal icebergQty,
          @JsonProperty("isWorking") Boolean isWorking,
          @JsonProperty("orderId") Long orderId,
          @JsonProperty("origQty") BigDecimal origQty,
          @JsonProperty("price") BigDecimal price,
          @JsonProperty("side") String side,
          @JsonProperty("status") String status,
          @JsonProperty("stopPrice") BigDecimal stopPrice,
          @JsonProperty("symbol") String symbol,
          @JsonProperty("time") Long time,
          @JsonProperty("transactTime") Long transactTime,
          @JsonProperty("timeInForce") String timeInForce,
          @JsonProperty("type") String type,
          @JsonProperty("updateTime") Long updateTime) {
    this.clientOrderId = clientOrderId;
    this.cummulativeQuoteQty = cummulativeQuoteQty;
    this.executedQty = executedQty;
    this.icebergQty = icebergQty;
    this.isWorking = isWorking;
    this.orderId = orderId;
    this.origQty = origQty;
    this.price = price;
    this.side = OrderSide.valueOf(side);
    this.status = OrderStatus.valueOf(status);
    this.stopPrice = stopPrice;
    this.symbol = BinanceAdapters.adaptSymbol(symbol);
    this.time = time;
    this.transactTime = transactTime;
    this.timeInForce = TimeInForce.valueOf(timeInForce);
    this.type = OrderType.valueOf(type);
    this.updateTime = updateTime;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public void setClientOrderId(String clientOrderId) {
    this.clientOrderId = clientOrderId;
  }

  public BigDecimal getCummulativeQuoteQty() {
    return cummulativeQuoteQty;
  }

  public void setCummulativeQuoteQty(BigDecimal cummulativeQuoteQty) {
    this.cummulativeQuoteQty = cummulativeQuoteQty;
  }

  public BigDecimal getExecutedQty() {
    return executedQty;
  }

  public void setExecutedQty(BigDecimal executedQty) {
    this.executedQty = executedQty;
  }

  public BigDecimal getIcebergQty() {
    return icebergQty;
  }

  public void setIcebergQty(BigDecimal icebergQty) {
    this.icebergQty = icebergQty;
  }

  public Boolean getWorking() {
    return isWorking;
  }

  public void setWorking(Boolean working) {
    isWorking = working;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public BigDecimal getOrigQty() {
    return origQty;
  }

  public void setOrigQty(BigDecimal origQty) {
    this.origQty = origQty;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public OrderSide getSide() {
    return side;
  }

  public void setSide(OrderSide side) {
    this.side = side;
  }

  public Long getTransactTime() {
    return transactTime;
  }

  public void setTransactTime(Long transactTime) {
    this.transactTime = transactTime;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  public void setStopPrice(BigDecimal stopPrice) {
    this.stopPrice = stopPrice;
  }

  public CurrencyPair getSymbol() {
    return symbol;
  }

  public void setSymbol(CurrencyPair symbol) {
    this.symbol = symbol;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public TimeInForce getTimeInForce() {
    return timeInForce;
  }

  public void setTimeInForce(TimeInForce timeInForce) {
    this.timeInForce = timeInForce;
  }

  public OrderType getType() {
    return type;
  }

  public void setType(OrderType type) {
    this.type = type;
  }

  public Long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Long updateTime) {
    this.updateTime = updateTime;
  }
}
