package org.knowm.xchange.liqui.dto.trade.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.liqui.dto.LiquiStat;
import org.knowm.xchange.liqui.dto.marketdata.LiquiResult;
import org.knowm.xchange.liqui.dto.trade.LiquiCancelOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiquiCancelOrderResult extends LiquiResult<LiquiCancelOrder> {

  private final boolean success;
  private final LiquiStat stat;

  public LiquiCancelOrderResult(
      @JsonProperty("success") final boolean success,
      @JsonProperty("return") final LiquiCancelOrder result,
      @JsonProperty("stat") final LiquiStat stat,
      @JsonProperty("error") final String error) {
    super(result, error);
    this.success = success;
    this.stat = stat;
  }

  public boolean isSuccess() {
    return success;
  }

  public LiquiStat getStat() {
    return stat;
  }

  @Override
  public String toString() {
    return "LiquiActiveOrdersResult{" + "success=" + success + ", stat=" + stat + '}';
  }
}
