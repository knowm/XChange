package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class OpenOrdersReq {

  private String address;
  private String market;

  /** (address string) - Address to return open orders associated with */
  public OpenOrdersReq address(String address) {
    this.address = address;
    return this;
  }

  @ApiModelProperty("(address string) - Address to return open orders associated with")
  @JsonProperty("address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  /** */
  public OpenOrdersReq market(String market) {
    this.market = market;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("market")
  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OpenOrdersReq openOrdersReq = (OpenOrdersReq) o;
    return Objects.equals(address, openOrdersReq.address)
        && Objects.equals(market, openOrdersReq.market);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, market);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpenOrdersReq {\n");

    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    market: ").append(toIndentedString(market)).append("\n");
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
