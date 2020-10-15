package org.knowm.xchange.coindcx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class CoindcxOrderStatusResponse {

  private UUID id;
  private String market;
  private String order_type;
  private String side;
  private String status;
  private BigDecimal fee_amount;
  private BigDecimal fee;
  private BigDecimal total_quantity;
  private BigDecimal remaining_quantity;
  private BigDecimal avg_price;
  private BigDecimal price_per_unit;
  private Date created_at;
  private Date updated_at;

  public CoindcxOrderStatusResponse(
      @JsonProperty("id") UUID id,
      @JsonProperty("market") String market,
      @JsonProperty("order_type") String order_type,
      @JsonProperty("side") String side,
      @JsonProperty("status") String status,
      @JsonProperty("fee_amount") BigDecimal fee_amount,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("total_quantity") BigDecimal total_quantity,
      @JsonProperty("remaining_quantity") BigDecimal remaining_quantity,
      @JsonProperty("avg_price") BigDecimal avg_price,
      @JsonProperty("price_per_unit") BigDecimal price_per_unit,
      @JsonProperty("created_at") Date created_at,
      @JsonProperty("updated_at") Date updated_at) {
    this.id = id;
    this.market = market;
    this.order_type = order_type;
    this.side = side;
    this.status = status;
    this.fee_amount = fee_amount;
    this.fee = fee;
    this.total_quantity = total_quantity;
    this.remaining_quantity = remaining_quantity;
    this.avg_price = avg_price;
    this.price_per_unit = price_per_unit;
    this.created_at = created_at;
    this.updated_at = updated_at;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getOrder_type() {
    return order_type;
  }

  public void setOrder_type(String order_type) {
    this.order_type = order_type;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public BigDecimal getFee_amount() {
    return fee_amount;
  }

  public void setFee_amount(BigDecimal fee_amount) {
    this.fee_amount = fee_amount;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  public BigDecimal getTotal_quantity() {
    return total_quantity;
  }

  public void setTotal_quantity(BigDecimal total_quantity) {
    this.total_quantity = total_quantity;
  }

  public BigDecimal getRemaining_quantity() {
    return remaining_quantity;
  }

  public void setRemaining_quantity(BigDecimal remaining_quantity) {
    this.remaining_quantity = remaining_quantity;
  }

  public BigDecimal getAvg_price() {
    return avg_price;
  }

  public void setAvg_price(BigDecimal avg_price) {
    this.avg_price = avg_price;
  }

  public BigDecimal getPrice_per_unit() {
    return price_per_unit;
  }

  public void setPrice_per_unit(BigDecimal price_per_unit) {
    this.price_per_unit = price_per_unit;
  }

  public Date getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Date created_at) {
    this.created_at = created_at;
  }

  public Date getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(Date updated_at) {
    this.updated_at = updated_at;
  }

  @Override
  public String toString() {
    return "CoindcxOrderStatusResponse [id="
        + id
        + ", market="
        + market
        + ", order_type="
        + order_type
        + ", side="
        + side
        + ", status="
        + status
        + ", fee_amount="
        + fee_amount
        + ", fee="
        + fee
        + ", total_quantity="
        + total_quantity
        + ", remaining_quantity="
        + remaining_quantity
        + ", avg_price="
        + avg_price
        + ", price_per_unit="
        + price_per_unit
        + ", created_at="
        + created_at
        + ", updated_at="
        + updated_at
        + "]";
  }
}
