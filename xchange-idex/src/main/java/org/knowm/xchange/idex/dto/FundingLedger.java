package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class FundingLedger {

  private String depositNumber = "0";
  private String currency = "";
  private String amount = "";
  private String timestamp = "0";
  private String status;
  private String transactionHash = "";

  /** */
  public FundingLedger depositNumber(String depositNumber) {
    this.depositNumber = depositNumber;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("depositNumber")
  public String getDepositNumber() {
    return depositNumber;
  }

  public void setDepositNumber(String depositNumber) {
    this.depositNumber = depositNumber;
  }

  /** */
  public FundingLedger currency(String currency) {
    this.currency = currency;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  /** */
  public FundingLedger amount(String amount) {
    this.amount = amount;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("amount")
  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  /** */
  public FundingLedger timestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("timestamp")
  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  /** */
  public FundingLedger status(String status) {
    this.status = status;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  /** */
  public FundingLedger transactionHash(String transactionHash) {
    this.transactionHash = transactionHash;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("transactionHash")
  public String getTransactionHash() {
    return transactionHash;
  }

  public void setTransactionHash(String transactionHash) {
    this.transactionHash = transactionHash;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FundingLedger fundingLedger = (FundingLedger) o;
    return Objects.equals(depositNumber, fundingLedger.depositNumber)
        && Objects.equals(currency, fundingLedger.currency)
        && Objects.equals(amount, fundingLedger.amount)
        && Objects.equals(timestamp, fundingLedger.timestamp)
        && Objects.equals(status, fundingLedger.status)
        && Objects.equals(transactionHash, fundingLedger.transactionHash);
  }

  @Override
  public int hashCode() {
    return Objects.hash(depositNumber, currency, amount, timestamp, status, transactionHash);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FundingLedger {\n");

    sb.append("    depositNumber: ").append(toIndentedString(depositNumber)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    transactionHash: ").append(toIndentedString(transactionHash)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
