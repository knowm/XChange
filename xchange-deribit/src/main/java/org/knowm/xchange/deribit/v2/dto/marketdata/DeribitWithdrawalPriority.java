package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitWithdrawalPriority {

  @JsonProperty("value")
  private BigDecimal value;

  @JsonProperty("name")
  private String name;


  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "DeribitWithdrawalPriority{" +
            "value=" + value +
            ", name='" + name + '\'' +
            '}';
  }
}
