package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Ravi Pandit */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {

  private String oid;
  private String originalValue;
  private String unfilledAmount;
  private String originalAmount;
  private String book;
  private String createdAt;
  private String updatedAt;
  private String price;
  private String side;
  private String type;
  private String status;
  private String timeInForce;

  public Payload(
      @JsonProperty("oid") String oid,
      @JsonProperty("original_value") String originalValue,
      @JsonProperty("unfilled_amount") String unfilledAmount,
      @JsonProperty("original_amount") String originalAmount,
      @JsonProperty("book") String book,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("updated_at") String updatedAt,
      @JsonProperty("price") String price,
      @JsonProperty("side") String side,
      @JsonProperty("type") String type,
      @JsonProperty("status") String status,
      @JsonProperty("time_in_force") String timeInForce) {
    this.oid = oid;
    this.originalAmount = originalAmount;
    this.unfilledAmount = unfilledAmount;
    this.originalAmount = originalAmount;
    this.book = book;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.price = price;
    this.side = side;
    this.type = type;
    this.status = status;
    this.timeInForce = timeInForce;
  }

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public String getOriginalValue() {
    return originalValue;
  }

  public void setOriginalValue(String originalValue) {
    this.originalValue = originalValue;
  }

  public String getUnfilledAmount() {
    return unfilledAmount;
  }

  public void setUnfilledAmount(String unfilledAmount) {
    this.unfilledAmount = unfilledAmount;
  }

  public String getOriginalAmount() {
    return originalAmount;
  }

  public void setOriginalAmount(String originalAmount) {
    this.originalAmount = originalAmount;
  }

  public String getBook() {
    return book;
  }

  public void setBook(String book) {
    this.book = book;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTimeInForce() {
    return timeInForce;
  }

  public void setTimeInForce(String timeInForce) {
    this.timeInForce = timeInForce;
  }

  @Override
  public String toString() {
    return "Payload [oid="
        + oid
        + ", originalValue="
        + originalValue
        + ", unfilledAmount="
        + unfilledAmount
        + ", originalAmount="
        + originalAmount
        + ", book="
        + book
        + ", createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + ", price="
        + price
        + ", side="
        + side
        + ", type="
        + type
        + ", status="
        + status
        + ", timeInForce="
        + timeInForce
        + "]";
  }
}
