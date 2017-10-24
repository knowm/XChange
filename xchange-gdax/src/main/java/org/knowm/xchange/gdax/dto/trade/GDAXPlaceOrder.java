package org.knowm.xchange.gdax.dto.trade;

import java.math.BigDecimal;
import java.util.Set;

import org.knowm.xchange.dto.Order.IOrderFlags;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GDAXPlaceOrder {
  @JsonProperty("size")
  private final BigDecimal size;
  @JsonProperty("price")
  private final BigDecimal price;
  @JsonProperty("side")
  private final String side;
  @JsonProperty("product_id")
  private final String productId;
  @JsonProperty("type")
  private final String type;

  private final Set<IOrderFlags> orderFlags;

  public GDAXPlaceOrder(BigDecimal size, BigDecimal price, String side, String productId, String type, Set<IOrderFlags> orderFlags) {
    this.size = size;
    this.price = price;
    this.side = side;
    this.productId = productId;
    this.type = type;
    this.orderFlags = orderFlags;
  }

  public BigDecimal getSize() {
    return size;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getSide() {
    return side;
  }

  public String getProductId() {
    return productId;
  }

  public String getType() {
    return type;
  }

  @JsonProperty("time_in_force")
  @JsonInclude(Include.NON_NULL)
  public String getTimeInForce() {
    if (orderFlags == null) {
      return null;
    } else if (orderFlags.contains(GDAXOrderFlags.FILL_OR_KILL)) {
      return "FOK";
    } else if (orderFlags.contains(GDAXOrderFlags.IMMEDIATE_OR_CANCEL)) {
      return "IOC";
    } else {
      return null; // defaults to GTC
    }
  }

  @JsonProperty("post_only")
  @JsonInclude(Include.NON_NULL)
  public Boolean isPostOnly() {
    if (orderFlags == null) {
      return null;
    } else if (orderFlags.contains(GDAXOrderFlags.POST_ONLY)) {
      return Boolean.TRUE;
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CoinbaseExPlaceOrder [size=");
    builder.append(size);
    builder.append(", price=");
    builder.append(price);
    builder.append(", side=");
    builder.append(side);
    builder.append(", type=");
    builder.append(type);
    builder.append(", productId=");
    builder.append(productId);
    builder.append(", orderFlags=");
    builder.append(orderFlags);
    builder.append("]");
    return builder.toString();
  }
}
