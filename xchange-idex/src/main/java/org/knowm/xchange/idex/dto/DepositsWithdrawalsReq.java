package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class DepositsWithdrawalsReq {

  private String address;
  private String start;
  private String end;

  /** (address string) - Address to query deposit/withdrawal history for */
  public DepositsWithdrawalsReq address(String address) {
    this.address = address;
    return this;
  }

  @ApiModelProperty("(address string) - Address to query deposit/withdrawal history for")
  @JsonProperty("address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  /** (number) - Inclusive starting UNIX timestamp of returned results, defaults to 0 */
  public DepositsWithdrawalsReq start(String start) {
    this.start = start;
    return this;
  }

  @ApiModelProperty(
      "(number) - Inclusive starting UNIX timestamp of returned results, defaults to 0")
  @JsonProperty("start")
  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  /**
   * (number) - Inclusive ending UNIX timestamp of returned results, defaults to current timestamp
   */
  public DepositsWithdrawalsReq end(String end) {
    this.end = end;
    return this;
  }

  @ApiModelProperty(
      "(number) - Inclusive ending UNIX timestamp of returned results, defaults to current timestamp")
  @JsonProperty("end")
  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DepositsWithdrawalsReq depositsWithdrawalsReq = (DepositsWithdrawalsReq) o;
    return Objects.equals(address, depositsWithdrawalsReq.address)
        && Objects.equals(start, depositsWithdrawalsReq.start)
        && Objects.equals(end, depositsWithdrawalsReq.end);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, start, end);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DepositsWithdrawalsReq {\n");

    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    start: ").append(toIndentedString(start)).append("\n");
    sb.append("    end: ").append(toIndentedString(end)).append("\n");
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
