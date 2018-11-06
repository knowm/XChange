package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import org.knowm.xchange.dto.account.Fee;

public class FeeTier implements Serializable, Comparable<FeeTier> {
  @JsonProperty("begin_quantity")
  public final BigDecimal beginQuantity;

  @JsonProperty("fee")
  public Fee fee;

  public FeeTier(
      @JsonProperty("begin_quantity") BigDecimal beginQuantity, @JsonProperty("fee") Fee fee) {
    this.beginQuantity = beginQuantity;
    this.fee = fee;
  }

  @Override
  public String toString() {
    return "FeeTier [beginQuantity=" + beginQuantity + ", fee=" + fee;
  }

  @Override
  public int compareTo(FeeTier other) {
    return beginQuantity.compareTo(other.beginQuantity);
  }
}
