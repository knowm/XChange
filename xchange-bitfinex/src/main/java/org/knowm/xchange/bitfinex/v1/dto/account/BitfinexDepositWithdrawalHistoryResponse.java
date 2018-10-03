package org.knowm.xchange.bitfinex.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.dto.account.FundingRecord;

public class BitfinexDepositWithdrawalHistoryResponse {

  @JsonProperty("id")
  private long id;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("method")
  private String method;

  @JsonProperty("type")
  private FundingRecord.Type type;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("description")
  private String description;

  @JsonProperty("address")
  private String address;

  @JsonProperty("status")
  private String status;

  @JsonProperty("timestamp")
  private BigDecimal timestamp;

  @JsonProperty("txid")
  private String txid;

  @JsonProperty("timestamp_created")
  private BigDecimal timestampCreated;

  @JsonProperty("fee")
  private BigDecimal fee;

  public BitfinexDepositWithdrawalHistoryResponse(
      @JsonProperty("id") Long id,
      @JsonProperty("currency") String currency,
      @JsonProperty("method") String method,
      @JsonProperty("type") FundingRecord.Type type,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("description") String description,
      @JsonProperty("address") String address,
      @JsonProperty("status") String status,
      @JsonProperty("timestamp") BigDecimal timestamp,
      @JsonProperty("txid") String txid,
      @JsonProperty("timestamp_created") BigDecimal timestampCreated,
      @JsonProperty("fee") BigDecimal fee) {
    this.id = id;
    this.currency = currency;
    this.method = method;
    this.type = type;
    this.amount = amount;
    this.description = description;
    this.address = address;
    this.status = status;
    this.timestamp = timestamp;
    this.txid = txid;
    this.timestampCreated = timestampCreated;
    this.fee = fee;
  }

  @Override
  public String toString() {
    return "BitfinexDepositWithdrawalHistoryResponse [id="
        + id
        + ", currency="
        + currency
        + ", method="
        + method
        + ", type="
        + type
        + ", amount="
        + amount
        + ", description="
        + description
        + ", address="
        + address
        + ", status="
        + status
        + ", timestamp="
        + getTimestamp()
        + ", txid="
        + txid
        + ", timestampCreated="
        + getTimestampCreated()
        + ", fee="
        + fee
        + "]";
  }

  public long getId() {
    return id;
  }

  public String getCurrency() {
    return currency;
  }

  public String getMethod() {
    return method;
  }

  public FundingRecord.Type getType() {
    return type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getDescription() {
    return description;
  }

  public String getAddress() {
    return address;
  }

  public String getStatus() {
    return status;
  }

  public Date getTimestamp() {
    return new Date(timestamp.scaleByPowerOfTen(3).longValue());
  }

  public String getTxid() {
    return txid;
  }

  public Date getTimestampCreated() {
    return new Date(timestampCreated.scaleByPowerOfTen(3).longValue());
  }

  public BigDecimal getFee() {
    return fee;
  }
}
