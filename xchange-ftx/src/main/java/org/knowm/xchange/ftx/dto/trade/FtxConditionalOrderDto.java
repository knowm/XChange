package org.knowm.xchange.ftx.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FtxConditionalOrderDto {

  private final Date createdAt;

  private final String future;
  private final String id;
  private final String market;
  private final BigDecimal orderPrice;
  private final boolean reduceOnly;
  private final FtxOrderSide side;
  private final BigDecimal size;
  private final FtxOrderStatus status;
  private final BigDecimal trailStart;
  private final BigDecimal trailValue;
  private final BigDecimal triggerPrice;
  private final Date triggeredAt;
  private final FtxConditionalOrderType type;
  private final FtxOrderType orderType;
  private final BigDecimal filledSize;
  private final BigDecimal avgFillPrice;
  private final boolean retryUntilFilled;

  @JsonCreator
  public FtxConditionalOrderDto(
      @JsonProperty("createdAt") Date createdAt,
      @JsonProperty("future") String future,
      @JsonProperty("id") String id,
      @JsonProperty("market") String market,
      @JsonProperty("orderPrice") BigDecimal orderPrice,
      @JsonProperty("reduceOnly") boolean reduceOnly,
      @JsonProperty("side") FtxOrderSide side,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("status") FtxOrderStatus status,
      @JsonProperty("trailStart") BigDecimal trailStart,
      @JsonProperty("trailValue") BigDecimal trailValue,
      @JsonProperty("triggerPrice") BigDecimal triggerPrice,
      @JsonProperty("triggeredAt") Date triggeredAt,
      @JsonProperty("type") FtxConditionalOrderType type,
      @JsonProperty("orderType") FtxOrderType orderType,
      @JsonProperty("filledSize") BigDecimal filledSize,
      @JsonProperty("avgFillPrice") BigDecimal avgFillPrice,
      @JsonProperty("retryUntilFilled") boolean retryUntilFilled) {
    this.createdAt = createdAt;
    this.future = future;
    this.id = id;
    this.market = market;
    this.orderPrice = orderPrice;
    this.reduceOnly = reduceOnly;
    this.side = side;
    this.size = size;
    this.status = status;
    this.trailStart = trailStart;
    this.trailValue = trailValue;
    this.triggerPrice = triggerPrice;
    this.triggeredAt = triggeredAt;
    this.type = type;
    this.orderType = orderType;
    this.filledSize = filledSize;
    this.avgFillPrice = avgFillPrice;
    this.retryUntilFilled = retryUntilFilled;
  }

  public Date getCreatedAt() {
    return createdAt;
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

  public BigDecimal getOrderPrice() {
    return orderPrice;
  }

  public boolean isReduceOnly() {
    return reduceOnly;
  }

  public FtxOrderSide getSide() {
    return side;
  }

  public BigDecimal getSize() {
    return size;
  }

  public FtxOrderStatus getStatus() {
    return status;
  }

  public BigDecimal getTrailStart() {
    return trailStart;
  }

  public BigDecimal getTrailValue() {
    return trailValue;
  }

  public BigDecimal getTriggerPrice() {
    return triggerPrice;
  }

  public Date getTriggeredAt() {
    return triggeredAt;
  }

  public FtxConditionalOrderType getType() {
    return type;
  }

  public FtxOrderType getOrderType() {
    return orderType;
  }

  public BigDecimal getFilledSize() {
    return filledSize;
  }

  public BigDecimal getAvgFillPrice() {
    return avgFillPrice;
  }

  public boolean isRetryUntilFilled() {
    return retryUntilFilled;
  }

  @Override
  public String toString() {
    return "FtxOrderDto{"
        + "createdAt="
        + createdAt
        + "future="
        + future
        + "id="
        + id
        + "market="
        + market
        + "orderPrice="
        + orderPrice
        + "reduceOnly="
        + reduceOnly
        + "side="
        + side
        + "size="
        + size
        + "status="
        + status
        + "trailStart="
        + trailStart
        + "trailValue="
        + trailValue
        + "triggerPrice="
        + triggerPrice
        + "triggeredAt="
        + triggeredAt
        + "type="
        + type
        + "orderType="
        + orderType
        + "filledSize="
        + filledSize
        + "avgFillPrice="
        + avgFillPrice
        + "retryUntilFilled="
        + retryUntilFilled
        + '}';
  }
}
