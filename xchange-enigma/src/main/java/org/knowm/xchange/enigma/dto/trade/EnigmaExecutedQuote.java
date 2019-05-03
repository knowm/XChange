package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class EnigmaExecutedQuote {

  private boolean result;
  private String message;
  private int orderId;
  private String rfqClientId;
  private int productId;
  private String productName;
  private String side;
  private Date createdAt;
  private BigDecimal quantity;
  private BigDecimal price;
  private BigDecimal nominal;
  private String infrastructure;

  public EnigmaExecutedQuote(
      @JsonProperty boolean result,
      @JsonProperty String message,
      @JsonProperty("order_id") int orderId,
      @JsonProperty("rfq_client_id") String rfqClientId,
      @JsonProperty("product_id") int productId,
      @JsonProperty("product_name") String productName,
      @JsonProperty String side,
      @JsonProperty("created_at") Date createdAt,
      @JsonProperty BigDecimal quantity,
      @JsonProperty BigDecimal price,
      @JsonProperty BigDecimal nominal,
      @JsonProperty("infra") String infrastructure) {
    this.result = result;
    this.message = message;
    this.orderId = orderId;
    this.rfqClientId = rfqClientId;
    this.productId = productId;
    this.productName = productName;
    this.side = side;
    this.createdAt = createdAt;
    this.quantity = quantity;
    this.price = price;
    this.nominal = nominal;
    this.infrastructure = infrastructure;
  }

  public boolean isResult() {
    return result;
  }

  public String getMessage() {
    return message;
  }

  public int getOrderId() {
    return orderId;
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

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getNominal() {
    return nominal;
  }

  public String getInfrastructure() {
    return infrastructure;
  }
}
