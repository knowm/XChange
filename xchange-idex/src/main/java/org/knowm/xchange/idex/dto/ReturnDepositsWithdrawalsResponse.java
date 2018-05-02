package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class ReturnDepositsWithdrawalsResponse {

  private java.util.List<FundingLedger> deposits = new java.util.ArrayList<>();
  private java.util.List<FundingLedger> withdrawals = new java.util.ArrayList<>();

  /** */
  public ReturnDepositsWithdrawalsResponse deposits(java.util.List<FundingLedger> deposits) {
    this.deposits = deposits;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("deposits")
  public java.util.List<FundingLedger> getDeposits() {
    return deposits;
  }

  public void setDeposits(java.util.List<FundingLedger> deposits) {
    this.deposits = deposits;
  }

  /** */
  public ReturnDepositsWithdrawalsResponse withdrawals(java.util.List<FundingLedger> withdrawals) {
    this.withdrawals = withdrawals;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("withdrawals")
  public java.util.List<FundingLedger> getWithdrawals() {
    return withdrawals;
  }

  public void setWithdrawals(java.util.List<FundingLedger> withdrawals) {
    this.withdrawals = withdrawals;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReturnDepositsWithdrawalsResponse returnDepositsWithdrawalsResponse =
        (ReturnDepositsWithdrawalsResponse) o;
    return Objects.equals(deposits, returnDepositsWithdrawalsResponse.deposits)
        && Objects.equals(withdrawals, returnDepositsWithdrawalsResponse.withdrawals);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deposits, withdrawals);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReturnDepositsWithdrawalsResponse {\n");

    sb.append("    deposits: ").append(toIndentedString(deposits)).append("\n");
    sb.append("    withdrawals: ").append(toIndentedString(withdrawals)).append("\n");
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
