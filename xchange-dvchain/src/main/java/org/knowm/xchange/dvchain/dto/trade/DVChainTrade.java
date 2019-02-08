package org.knowm.xchange.dvchain.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import org.knowm.xchange.dvchain.dto.account.DVChainUser;

public class DVChainTrade {
  private final String id;
  private final Instant createdAt;
  private final BigDecimal price;
  private final BigDecimal limitPrice;
  private final BigDecimal quantity;
  private final String side;
  private final String asset;
  private final String status;
  private final DVChainUser user;

  public DVChainTrade(
      @JsonProperty("_id") String id,
      @JsonProperty("createdAt") String createdAt,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("limitPrice") BigDecimal limitPrice,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("side") String side,
      @JsonProperty("user") DVChainUser user,
      @JsonProperty("asset") String asset,
      @JsonProperty("status") String status) {
    this.id = id;
    this.createdAt = Instant.parse(createdAt);
    this.price = price;
    this.quantity = quantity;
    this.side = side;
    this.user = user;
    this.asset = asset;
    this.status = status;
    this.limitPrice = limitPrice;
  }

  public String getId() {
    return id;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getSide() {
    return side;
  }

  public DVChainUser getUser() {
    return user;
  }

  public String getAsset() {
    return asset;
  }

  public String getStatus() {
    return status;
  }

  public BigDecimal getLimitPrice() {
    return limitPrice;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DVChainTrade {id=");
    builder.append(id);
    //        builder.append()
    return builder.toString();
  }
}
