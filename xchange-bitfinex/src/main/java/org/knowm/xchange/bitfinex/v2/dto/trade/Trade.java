package org.knowm.xchange.bitfinex.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** https://docs.bitfinex.com/v2/reference#rest-auth-trades-hist */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Setter
@Getter
@ToString
public class Trade {

  /** Trade database id */
  private String id;
  /** Pair (BTCUSD, …) */
  private String symbol;
  /** Execution timestamp millis */
  private long timestamp;
  /** Order id */
  private String orderId;
  /** Positive means buy, negative means sell */
  private BigDecimal execAmount;
  /** Execution price */
  private BigDecimal execPrice;
  /** Order type */
  private String orderType;
  /** Order price */
  private BigDecimal orderPrice;
  /** 1 if true, -1 if false */
  private int maker;
  /** Fee */
  private BigDecimal fee;
  /** Fee currency */
  private String feeCurrency;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public BigDecimal getExecAmount() {
    return execAmount;
  }

  public void setExecAmount(BigDecimal execAmount) {
    this.execAmount = execAmount;
  }

  public BigDecimal getExecPrice() {
    return execPrice;
  }

  public void setExecPrice(BigDecimal execPrice) {
    this.execPrice = execPrice;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  public BigDecimal getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(BigDecimal orderPrice) {
    this.orderPrice = orderPrice;
  }

  public int getMaker() {
    return maker;
  }

  public void setMaker(int maker) {
    this.maker = maker;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  public String getFeeCurrency() {
    return feeCurrency;
  }

  public void setFeeCurrency(String feeCurrency) {
    this.feeCurrency = feeCurrency;
  }

  public long getTimestamp() {
    return timestamp;
  }
}
