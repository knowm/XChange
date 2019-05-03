package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class EnigmaQuote {

  private boolean result;
  private String message;
  private String rfqClientId;
  private int productId;
  private String productName;
  private String side;
  private Date createdAt;
  private BigDecimal expiryAt;
  private BigDecimal quantity;
  private BigDecimal price;
  private BigDecimal nominal;

  public EnigmaQuote(
      @JsonProperty boolean result,
      @JsonProperty String message,
      @JsonProperty("rfq_client_id") String rfqClientId,
      @JsonProperty("product_id") int productId,
      @JsonProperty("product_name") String productName,
      @JsonProperty String side,
      @JsonProperty("created_at") Date createdAt,
      @JsonProperty BigDecimal quantity,
      @JsonProperty BigDecimal price,
      @JsonProperty BigDecimal nominal) {
    this.result = result;
    this.message = message;
    this.rfqClientId = rfqClientId;
    this.productId = productId;
    this.productName = productName;
    this.side = side;
    this.createdAt = createdAt;
    this.quantity = quantity;
    this.price = price;
    this.nominal = nominal;
  }

  public boolean isResult() {
    return result;
  }

  public String getMessage() {
    return message;
  }

  public String getRfqClientId() {
    return rfqClientId;
  }

  public int getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public String getSide() {
    return side;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public BigDecimal getExpiryAt() {
    return expiryAt;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getNominal() {
    return nominal;
  }
}
