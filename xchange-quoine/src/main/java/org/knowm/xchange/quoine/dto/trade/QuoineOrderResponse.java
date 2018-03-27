package org.knowm.xchange.quoine.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author timmolter */
public final class QuoineOrderResponse {

  private final String id;
  private final BigDecimal price;
  private final String status;
  private final BigDecimal quantity;
  private final BigDecimal filledQuantity;
  private final String productCode;
  private final String currencyPairCode;
  private final String createdAt;
  private final String updatedAt;
  private final String side;
  private final String orderType;
  private final Object notes;
  private final boolean success;

  /**
   * Constructor
   *
   * @param id
   * @param price
   * @param status
   * @param quantity
   * @param filledQuantity
   * @param productCode
   * @param currencyPairCode
   * @param createdAt
   * @param side
   * @param orderType
   * @param notes
   * @param success
   */
  public QuoineOrderResponse(
      @JsonProperty("id") String id,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("status") String status,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("filled_quantity") BigDecimal filledQuantity,
      @JsonProperty("product_code") String productCode,
      @JsonProperty("currency_pair_code") String currencyPairCode,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("updated_at") String updatedAt,
      @JsonProperty("side") String side,
      @JsonProperty("order_type") String orderType,
      @JsonProperty("notes") Object notes,
      @JsonProperty("success") boolean success) {
    this.id = id;
    this.price = price;
    this.status = status;
    this.quantity = quantity;
    this.filledQuantity = filledQuantity;
    this.productCode = productCode;
    this.currencyPairCode = currencyPairCode;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.side = side;
    this.orderType = orderType;
    this.notes = notes;
    this.success = success;
  }

  public String getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getStatus() {
    return status;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getFilledQuantity() {
    return filledQuantity;
  }

  public String getProductCode() {
    return productCode;
  }

  public String getCurrencyPairCode() {
    return currencyPairCode;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public String getSide() {
    return side;
  }

  public String getOrderType() {
    return orderType;
  }

  public Object getNotes() {
    return notes;
  }

  public boolean isSuccess() {
    return success;
  }

  @Override
  public String toString() {
    return "QuoineOrderResponse [id="
        + id
        + ", price="
        + price
        + ", status="
        + status
        + ", quantity="
        + quantity
        + ", filledQuantity="
        + filledQuantity
        + ", productCode="
        + productCode
        + ", currencyPairCode="
        + currencyPairCode
        + ", createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + ", side="
        + side
        + ", orderType="
        + orderType
        + ", notes="
        + notes
        + ", success="
        + success
        + "]";
  }
}
