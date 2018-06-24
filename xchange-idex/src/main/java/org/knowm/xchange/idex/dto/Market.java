package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class Market {

  private String market;

  /** */
  public Market market(String market) {
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
    Market market = (Market) o;
    return Objects.equals(market, market.market);
  }

  @Override
  public int hashCode() {
    return Objects.hash(market);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Market {\n");

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
