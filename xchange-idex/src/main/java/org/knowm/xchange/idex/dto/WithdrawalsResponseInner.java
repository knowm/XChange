package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class WithdrawalsResponseInner {

  private String withdrawalNumber = "0";
  private String currency = "";
  private String amount = "";
  private String timestamp = "0";
  private String transactionHash = "";
  private String status = "";

  /** */
  public WithdrawalsResponseInner withdrawalNumber(String withdrawalNumber) {
    this.withdrawalNumber = withdrawalNumber;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("withdrawalNumber")
  public String getWithdrawalNumber() {
    return withdrawalNumber;
  }

  public void setWithdrawalNumber(String withdrawalNumber) {
    this.withdrawalNumber = withdrawalNumber;
  }

  /** */
  public WithdrawalsResponseInner currency(String currency) {
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
  public WithdrawalsResponseInner amount(String amount) {
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
  public WithdrawalsResponseInner timestamp(String timestamp) {
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
  public WithdrawalsResponseInner transactionHash(String transactionHash) {
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

  /** */
  public WithdrawalsResponseInner status(String status) {
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

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WithdrawalsResponseInner withdrawalsResponseInner = (WithdrawalsResponseInner) o;
    return Objects.equals(withdrawalNumber, withdrawalsResponseInner.withdrawalNumber)
        && Objects.equals(currency, withdrawalsResponseInner.currency)
        && Objects.equals(amount, withdrawalsResponseInner.amount)
        && Objects.equals(timestamp, withdrawalsResponseInner.timestamp)
        && Objects.equals(transactionHash, withdrawalsResponseInner.transactionHash)
        && Objects.equals(status, withdrawalsResponseInner.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(withdrawalNumber, currency, amount, timestamp, transactionHash, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WithdrawalsResponseInner {\n");

    sb.append("    withdrawalNumber: ").append(toIndentedString(withdrawalNumber)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    transactionHash: ").append(toIndentedString(transactionHash)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
