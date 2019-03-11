package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class CancelReq {

  private String orderHash;
  private java.math.BigInteger nonce;
  private String address;
  private java.math.BigInteger v;
  private String r;
  private String s;

  /** (256-bit hex string) - The raw hash of the order you are cancelling */
  public CancelReq orderHash(String orderHash) {
    this.orderHash = orderHash;
    return this;
  }

  @ApiModelProperty("(256-bit hex string) - The raw hash of the order you are cancelling")
  @JsonProperty("orderHash")
  public String getOrderHash() {
    return orderHash;
  }

  public void setOrderHash(String orderHash) {
    this.orderHash = orderHash;
  }

  /** (uint256) - One time number associated with the address */
  public CancelReq nonce(java.math.BigInteger nonce) {
    this.nonce = nonce;
    return this;
  }

  @ApiModelProperty("(uint256) - One time number associated with the address")
  @JsonProperty("nonce")
  public java.math.BigInteger getNonce() {
    return nonce;
  }

  public void setNonce(java.math.BigInteger nonce) {
    this.nonce = nonce;
  }

  /** (address string) - The address you are sending the cancel from, must own the order */
  public CancelReq address(String address) {
    this.address = address;
    return this;
  }

  @ApiModelProperty(
      "(address string) - The address you are sending the cancel from, must own the order")
  @JsonProperty("address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  /** - ... */
  public CancelReq v(java.math.BigInteger v) {
    this.v = v;
    return this;
  }

  @ApiModelProperty("- ...")
  @JsonProperty("v")
  public java.math.BigInteger getV() {
    return v;
  }

  public void setV(java.math.BigInteger v) {
    this.v = v;
  }

  /** - ... */
  public CancelReq r(String r) {
    this.r = r;
    return this;
  }

  @ApiModelProperty("- ...")
  @JsonProperty("r")
  public String getR() {
    return r;
  }

  public void setR(String r) {
    this.r = r;
  }

  /** - v, r, and s refer to the values produced by signing the message */
  public CancelReq s(String s) {
    this.s = s;
    return this;
  }

  @ApiModelProperty("- v, r, and s refer to the values produced by signing the message")
  @JsonProperty("s")
  public String getS() {
    return s;
  }

  public void setS(String s) {
    this.s = s;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CancelReq cancelReq = (CancelReq) o;
    return Objects.equals(orderHash, cancelReq.orderHash)
        && Objects.equals(nonce, cancelReq.nonce)
        && Objects.equals(address, cancelReq.address)
        && Objects.equals(v, cancelReq.v)
        && Objects.equals(r, cancelReq.r)
        && Objects.equals(s, cancelReq.s);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderHash, nonce, address, v, r, s);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CancelReq {\n");

    sb.append("    orderHash: ").append(toIndentedString(orderHash)).append("\n");
    sb.append("    nonce: ").append(toIndentedString(nonce)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    v: ").append(toIndentedString(v)).append("\n");
    sb.append("    r: ").append(toIndentedString(r)).append("\n");
    sb.append("    s: ").append(toIndentedString(s)).append("\n");
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
