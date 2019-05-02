package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public final class EnigmaNewOrder {

  private boolean result;
  private String message;
  private int id;
  private int productId;
  private String productName;
  private String side;
  private Date sent;
  private BigDecimal quantity;
  private BigDecimal price;
  private BigDecimal nominal;
  private String infrastructure;

  public EnigmaNewOrder(
      @JsonProperty("result") boolean result,
      @JsonProperty("message") String message,
      @JsonProperty("id") int id,
      @JsonProperty("product_id") int productId,
      @JsonProperty("product_name") String productName,
      @JsonProperty("side") String side,
      @JsonProperty("sent") Date sent,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("nominal") BigDecimal nominal,
      @JsonProperty("infra") String infrastructure) {
    this.result = result;
    this.message = message;
    this.id = id;
    this.productId = productId;
    this.productName = productName;
    this.side = side;
    this.sent = sent;
    this.quantity = quantity;
    this.price = price;
    this.nominal = nominal;
    this.infrastructure = infrastructure;
  }

  public String getInfrastructure() {
    return infrastructure;
  }

  public BigDecimal getNominal() {
    return nominal;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public Date getSent() {
    return sent;
  }

  public String getSide() {
    return side;
  }

  public String getProductName() {
    return productName;
  }

  public int getProductId() {
    return productId;
  }

  public int getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  public boolean isResult() {
    return result;
  }
}
