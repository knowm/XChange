package org.knowm.xchange.ftx.dto.trade;

import java.math.BigDecimal;

public class FtxConditionalOrderRequestPayload {

  private String market;
  private FtxOrderSide side;
  private BigDecimal size;
  private FtxConditionalOrderType type;
  private boolean reduceOnly;
  private boolean retryUntilFilled;
  private BigDecimal orderPrice;
  private BigDecimal triggerPrice;
  private BigDecimal trailValue;

  public FtxConditionalOrderRequestPayload(
      String market,
      FtxOrderSide side,
      BigDecimal size,
      FtxConditionalOrderType type,
      boolean reduceOnly,
      boolean retryUntilFilled,
      BigDecimal orderPrice,
      BigDecimal triggerPrice,
      BigDecimal trailValue) {
    this.market = market;
    this.side = side;
    this.size = size;
    this.type = type;
    this.reduceOnly = reduceOnly;
    this.retryUntilFilled = retryUntilFilled;
    this.orderPrice = orderPrice;
    this.triggerPrice = triggerPrice;
    this.trailValue = trailValue;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public FtxOrderSide getSide() {
    return side;
  }

  public void setSide(FtxOrderSide side) {
    this.side = side;
  }

  public BigDecimal getSize() {
    return size;
  }

  public void setSize(BigDecimal size) {
    this.size = size;
  }

  public FtxConditionalOrderType getType() {
    return type;
  }

  public void setType(FtxConditionalOrderType type) {
    this.type = type;
  }

  public boolean isReduceOnly() {
    return reduceOnly;
  }

  public void setReduceOnly(boolean reduceOnly) {
    this.reduceOnly = reduceOnly;
  }

  public boolean isRetryUntilFilled() {
    return retryUntilFilled;
  }

  public void setRetryUntilFilled(boolean retryUntilFilled) {
    this.retryUntilFilled = retryUntilFilled;
  }

  public BigDecimal getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(BigDecimal orderPrice) {
    this.orderPrice = orderPrice;
  }

  public BigDecimal getTriggerPrice() {
    return triggerPrice;
  }

  public void setTriggerPrice(BigDecimal triggerPrice) {
    this.triggerPrice = triggerPrice;
  }

  public BigDecimal getTrailValue() {
    return trailValue;
  }

  public void setTrailValue(BigDecimal trailValue) {
    this.trailValue = trailValue;
  }

  @Override
  public String toString() {
    return "FtxConditionalOrderRequestPayload{"
        + "market='"
        + market
        + '\''
        + ", side="
        + side
        + ", size="
        + size
        + ", type="
        + type
        + ", reduceOnly="
        + reduceOnly
        + ", retryUntilFilled="
        + retryUntilFilled
        + ", orderPrice="
        + orderPrice
        + ", triggerPrice="
        + triggerPrice
        + ", trailValue="
        + trailValue
        + '}';
  }
}
