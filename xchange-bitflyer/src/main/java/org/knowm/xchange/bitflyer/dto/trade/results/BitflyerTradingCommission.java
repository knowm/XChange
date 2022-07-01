package org.knowm.xchange.bitflyer.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitflyerTradingCommission {
  @JsonProperty("commission_rate")
  private BigDecimal commissionRate;

  public BigDecimal getCommissionRate() {
    return commissionRate;
  }

  public void setCommissionRate(BigDecimal commissionRate) {
    this.commissionRate = commissionRate;
  }

  @Override
  public String toString() {
    return "BitflyerTradingCommission{" + "commissionRate=" + commissionRate + '}';
  }
}
