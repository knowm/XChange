package org.knowm.xchange.lgo.dto.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class LgoUserTrade {

  private final String id;
  private final String orderId;
  private final String productId;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final Date creationDate;
  private final BigDecimal fees;
  private final String side;
  private final String liquidity;

  public LgoUserTrade(
      @JsonProperty("id") String id,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("product_id") String productId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("creation_date")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          Date creationDate,
      @JsonProperty("fees") BigDecimal fees,
      @JsonProperty("side") String side,
      @JsonProperty("liquidity") String liquidity) {
    this.id = id;
    this.orderId = orderId;
    this.productId = productId;
    this.price = price;
    this.quantity = quantity;
    this.creationDate = creationDate;
    this.fees = fees;
    this.side = side;
    this.liquidity = liquidity;
  }

  public String getId() {
    return id;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getProductId() {
    return productId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public BigDecimal getFees() {
    return fees;
  }

  public String getSide() {
    return side;
  }

  public String getLiquidity() {
    return liquidity;
  }
}
