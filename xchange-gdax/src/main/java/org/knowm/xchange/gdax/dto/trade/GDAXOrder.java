package org.knowm.xchange.gdax.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class GDAXOrder {
  private final String id;
  private final BigDecimal price;
  private final BigDecimal size;
  private final String productId;
  private final String side;
  private final String createdAt;
  private final String doneAt;
  private final BigDecimal filledSize;
  private final BigDecimal fillFees;
  private final String status;
  private final boolean settled;
  private final String type;
  private final String doneReason;
  private final BigDecimal executedvalue;
  private final String stop;
  private final BigDecimal stopPrice;

  public GDAXOrder(
      @JsonProperty("id") String id,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("product_id") String productId,
      @JsonProperty("side") String side,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("done_at") String doneAt,
      @JsonProperty("filled_size") BigDecimal filledSize,
      @JsonProperty("fill_fees") BigDecimal fillFees,
      @JsonProperty("status") String status,
      @JsonProperty("settled") boolean settled,
      @JsonProperty("type") String type,
      @JsonProperty("done_reason") String doneReason,
      @JsonProperty("executed_value") BigDecimal executedValue,
      @JsonProperty("stop") String stop,
      @JsonProperty("stop_price") BigDecimal stopPrice) {
    this.id = id;
    this.price = price;
    this.size = size;
    this.productId = productId;
    this.side = side;
    this.createdAt = createdAt;
    this.doneAt = doneAt;
    this.filledSize = filledSize;
    this.fillFees = fillFees;
    this.status = status;
    this.settled = settled;
    this.type = type;
    this.doneReason = doneReason;
    this.executedvalue = executedValue;
    this.stop = stop;
    this.stopPrice = stopPrice;
  }

  public String getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getSize() {
    return size;
  }

  public String getProductId() {
    return productId;
  }

  public String getSide() {
    return side;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getDoneAt() {
    return doneAt;
  }

  public BigDecimal getFilledSize() {
    return filledSize;
  }

  public BigDecimal getFillFees() {
    return fillFees;
  }

  public String getStatus() {
    return status;
  }

  public boolean isSettled() {
    return settled;
  }

  public String getType() {
    return type;
  }

  public String getDoneReason() {
    return doneReason;
  }

  public BigDecimal getExecutedvalue() {
    return executedvalue;
  }

  public String getStop() {
    return stop;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CoinbaseExOrder [id=");
    builder.append(id);
    builder.append(", price=");
    builder.append(price);
    builder.append(", size=");
    builder.append(size);
    builder.append(", productId=");
    builder.append(productId);
    builder.append(", side=");
    builder.append(side);
    builder.append(", createdAt=");
    builder.append(createdAt);
    builder.append(", doneAt=");
    builder.append(doneAt);
    builder.append(", filledSize=");
    builder.append(filledSize);
    builder.append(", fillFees=");
    builder.append(fillFees);
    builder.append(", status=");
    builder.append(status);
    builder.append(", settled=");
    builder.append(settled);
    builder.append(", stop=");
    builder.append(stop);
    builder.append(", stopPrice=");
    builder.append(stopPrice);
    builder.append("]");
    return builder.toString();
  }
}
