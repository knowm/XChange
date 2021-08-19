package org.knowm.xchange.ftx.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.dto.Order;

public class FtxOrderDto {

  private final Date createdAt;

  private final BigDecimal filledSize;

  private final String future;

  private final String id;

  private final String market;

  private final BigDecimal price;

  private final BigDecimal avgFillPrice;

  private final BigDecimal remainingSize;

  private final FtxOrderSide side;

  private final BigDecimal size;

  private final Order.OrderStatus status;

  private final FtxOrderType type;

  private final boolean reduceOnly;

  private final boolean ioc;

  private final boolean postOnly;

  private final String clientId;

  @JsonCreator
  public FtxOrderDto(
      @JsonProperty("createdAt") Date createdAt,
      @JsonProperty("filledSize") BigDecimal filledSize,
      @JsonProperty("future") String future,
      @JsonProperty("id") String id,
      @JsonProperty("market") String market,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("remainingSize") BigDecimal remainingSize,
      @JsonProperty("avgFillPrice") BigDecimal avgFillPrice,
      @JsonProperty("side") FtxOrderSide side,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("status") String status,
      @JsonProperty("type") FtxOrderType type,
      @JsonProperty("reduceOnly") boolean reduceOnly,
      @JsonProperty("ioc") boolean ioc,
      @JsonProperty("postOnly") boolean postOnly,
      @JsonProperty("clientId") String clientId) {
    this.createdAt = createdAt;
    this.filledSize = filledSize;
    this.future = future;
    this.id = id;
    this.market = market;
    this.price = price;
    this.remainingSize = remainingSize;
    this.avgFillPrice = avgFillPrice;
    this.side = side;
    this.size = size;
    this.status = Order.OrderStatus.valueOf(status.toUpperCase());
    this.type = type;
    this.reduceOnly = reduceOnly;
    this.ioc = ioc;
    this.postOnly = postOnly;
    this.clientId = clientId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public BigDecimal getFilledSize() {
    return filledSize;
  }

  public String getFuture() {
    return future;
  }

  public String getId() {
    return id;
  }

  public String getMarket() {
    return market;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAvgFillPrice() {
    return avgFillPrice;
  }

  public BigDecimal getRemainingSize() {
    return remainingSize;
  }

  public FtxOrderSide getSide() {
    return side;
  }

  public BigDecimal getSize() {
    return size;
  }

  public Order.OrderStatus getStatus() {
    return status;
  }

  public FtxOrderType getType() {
    return type;
  }

  public boolean isReduceOnly() {
    return reduceOnly;
  }

  public boolean isIoc() {
    return ioc;
  }

  public boolean isPostOnly() {
    return postOnly;
  }

  public String getClientId() {
    return clientId;
  }

  @Override
  public String toString() {
    return "FtxOrderDto{"
        + "createdAt="
        + createdAt
        + ", filledSize="
        + filledSize
        + ", future='"
        + future
        + '\''
        + ", id='"
        + id
        + '\''
        + ", market='"
        + market
        + '\''
        + ", price="
        + price
        + ", avgFillPrice="
        + avgFillPrice
        + ", remainingSize="
        + remainingSize
        + ", side="
        + side
        + ", size="
        + size
        + ", status="
        + status
        + ", type="
        + type
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
