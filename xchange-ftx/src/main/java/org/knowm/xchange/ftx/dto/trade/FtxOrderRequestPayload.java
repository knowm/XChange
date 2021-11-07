package org.knowm.xchange.ftx.dto.trade;

import java.math.BigDecimal;

public class FtxOrderRequestPayload {

  private String market;

  private FtxOrderSide side;

  private BigDecimal price;

  private FtxOrderType type;

  private BigDecimal size;

  private boolean reduceOnly;

  private boolean ioc;

  private boolean postOnly;

  private String clientId;

  public FtxOrderRequestPayload(
      String market,
      FtxOrderSide side,
      BigDecimal price,
      FtxOrderType type,
      BigDecimal size,
      boolean reduceOnly,
      boolean ioc,
      boolean postOnly,
      String clientId) {
    this.market = market;
    this.side = side;
    this.price = price;
    this.type = type;
    this.size = size;
    this.reduceOnly = reduceOnly;
    this.ioc = ioc;
    this.postOnly = postOnly;
    this.clientId = clientId;
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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public FtxOrderType getType() {
    return type;
  }

  public void setType(FtxOrderType type) {
    this.type = type;
  }

  public BigDecimal getSize() {
    return size;
  }

  public void setSize(BigDecimal size) {
    this.size = size;
  }

  public boolean isReduceOnly() {
    return reduceOnly;
  }

  public void setReduceOnly(boolean reduceOnly) {
    this.reduceOnly = reduceOnly;
  }

  public boolean isIoc() {
    return ioc;
  }

  public void setIoc(boolean ioc) {
    this.ioc = ioc;
  }

  public boolean isPostOnly() {
    return postOnly;
  }

  public void setPostOnly(boolean postOnly) {
    this.postOnly = postOnly;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  @Override
  public String toString() {
    return "FtxOrderRequestPOJO{"
        + "market='"
        + market
        + '\''
        + ", side="
        + side
        + ", price="
        + price
        + ", type="
        + type
        + ", size="
        + size
        + ", reduceOnly="
        + reduceOnly
        + ", ioc="
        + ioc
        + ", postOnly="
        + postOnly
        + ", clientId='"
        + clientId
        + '\''
        + '}';
  }
}
