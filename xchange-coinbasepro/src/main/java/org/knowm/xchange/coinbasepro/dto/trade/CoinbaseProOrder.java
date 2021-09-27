package org.knowm.xchange.coinbasepro.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinbaseProOrder {
  private final String id;
  private final BigDecimal price;
  private final BigDecimal size;
  private final String productId;
  private final String clientOid;
  private final String side;
  private final String createdAt;
  private final String doneAt;
  private final BigDecimal filledSize;
  private final BigDecimal fillFees;
  private final String status;
  private final boolean settled;
  private final String type;
  private final String doneReason;
  private final BigDecimal executedValue;
  private final String stop;
  private final BigDecimal stopPrice;

  public CoinbaseProOrder(
      @JsonProperty("id") String id,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("product_id") String productId,
      @JsonProperty("client_oid") String clientOid,
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
    this.clientOid = clientOid;
    this.side = side;
    this.createdAt = createdAt;
    this.doneAt = doneAt;
    this.filledSize = filledSize;
    this.fillFees = fillFees;
    this.status = status;
    this.settled = settled;
    this.type = type;
    this.doneReason = doneReason;
    this.executedValue = executedValue;
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

  public String getClientOid() {
    return clientOid;
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

  public BigDecimal getExecutedValue() {
    return executedValue;
  }

  public String getStop() {
    return stop;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  @Override
  public boolean equals(Object obj) {
    CoinbaseProOrder order = (CoinbaseProOrder) obj;
    return
            (id == null && order.id == null || id != null && this.id.equals(order.id)) &&
                    (this.price == null && order.price == null || this.price != null && this.price.compareTo(order.price) == 0) &&
                    (this.size == null && order.size == null || this.size != null && this.size.compareTo(order.size) == 0) &&
                    (this.productId == null && order.productId == null || this.productId != null && this.productId.equals(order.productId)) &&
                    (this.side == null && order.side == null || this.side != null && this.side.equals(order.side)) &&
                    (this.filledSize == null && order.filledSize == null || this.filledSize != null && this.filledSize.compareTo(order.filledSize) == 0) &&
                    (this.fillFees == null && order.fillFees == null || this.fillFees != null && this.fillFees.compareTo(order.fillFees) == 0) &&
                    (this.status == null && order.status == null || this.status != null && this.status.equals(order.status)) &&
                    (this.type == null && order.type == null || this.type != null && this.type.equals(order.type)) &&
                    (this.doneReason == null && order.doneReason == null || this.doneReason != null && this.doneReason.equals(order.doneReason)) &&
                    (this.executedValue == null && order.executedValue == null || this.executedValue != null && this.executedValue.compareTo(order.executedValue) == 0);
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
    builder.append(", clientOid=");
    builder.append(clientOid);
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
    builder.append(", type=");
    builder.append(type);
    builder.append(", doneReason=");
    builder.append(doneReason);
    builder.append(", executedValue=");
    builder.append(executedValue);
    builder.append(", stop=");
    builder.append(stop);
    builder.append(", stopPrice=");
    builder.append(stopPrice);
    builder.append("]");
    return builder.toString();
  }
}
