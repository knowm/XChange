package org.knowm.xchange.dsx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class DsxTransaction {

  private final String id;
  private final String index;
  private final String currency;
  private final BigDecimal amount;
  private final BigDecimal fee;
  private final BigDecimal networkFee;
  private final String address;
  private final String paymentId;
  private final String hash;
  private final String status;
  private final String type;
  private final Date createdAt;
  private final Date updatedAt;

  public DsxTransaction(
      @JsonProperty("id") String id,
      @JsonProperty("index") String index,
      @JsonProperty("currency") String currency,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("networkFee") BigDecimal networkFee,
      @JsonProperty("address") String address,
      @JsonProperty("paymentId") String paymentId,
      @JsonProperty("hash") String hash,
      @JsonProperty("status") String status,
      @JsonProperty("type") String type,
      @JsonProperty("createdAt") Date createdAt,
      @JsonProperty("updatedAt") Date updatedAt) {

    this.id = id;
    this.index = index;
    this.currency = currency;
    this.amount = amount;
    this.fee = fee;
    this.networkFee = networkFee;
    this.address = address;
    this.paymentId = paymentId;
    this.hash = hash;
    this.status = status;
    this.type = type;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public String getId() {
    return id;
  }

  public String getIndex() {
    return index;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getNetworkFee() {
    return networkFee;
  }

  public String getAddress() {
    return address;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public String getHash() {
    return hash;
  }

  public String getStatus() {
    return status;
  }

  public String getType() {
    return type;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public String toString() {
    return "DsxTransaction{"
        + "id='"
        + id
        + '\''
        + ", index='"
        + index
        + '\''
        + ", currency='"
        + currency
        + '\''
        + ", amount='"
        + amount
        + '\''
        + ", fee='"
        + fee
        + '\''
        + ", networkFee='"
        + networkFee
        + '\''
        + ", address='"
        + address
        + '\''
        + ", paymentId='"
        + paymentId
        + '\''
        + ", hash='"
        + hash
        + '\''
        + ", status='"
        + status
        + '\''
        + ", type='"
        + type
        + '\''
        + ", createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + '}';
  }
}
