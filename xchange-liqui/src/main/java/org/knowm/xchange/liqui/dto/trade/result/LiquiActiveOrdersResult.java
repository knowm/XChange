package org.knowm.xchange.liqui.dto.trade.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.liqui.dto.LiquiStat;
import org.knowm.xchange.liqui.dto.marketdata.LiquiResult;
import org.knowm.xchange.liqui.dto.trade.LiquiOrderInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiquiActiveOrdersResult extends LiquiResult<Map<Long, LiquiOrderInfo>> {

  private final boolean success;
  private final LiquiStat stat;

  public LiquiActiveOrdersResult(
      @JsonProperty("success") final boolean success,
      @JsonProperty("return") final Map<Long, LiquiOrderInfo> result,
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
