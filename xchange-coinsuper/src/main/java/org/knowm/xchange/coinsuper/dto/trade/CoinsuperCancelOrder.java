package org.knowm.xchange.coinsuper.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CoinsuperCancelOrder {
  @JsonProperty("operate")
  private String operate;

  public CoinsuperCancelOrder() {}

  @JsonProperty("operate")
  public String getOperate() {
    return operate;
  }

  @JsonProperty("operate")
  public void setOperate(String operate) {
    this.operate = operate;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("operate", operate).toString();
  }
}
