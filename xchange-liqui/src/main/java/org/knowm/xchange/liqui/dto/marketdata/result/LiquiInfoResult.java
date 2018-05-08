package org.knowm.xchange.liqui.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPairInfo;
import org.knowm.xchange.liqui.dto.marketdata.LiquiResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiquiInfoResult extends LiquiResult<Map<String, LiquiPairInfo>> {

  private final long serverTime;

  public LiquiInfoResult(
      @JsonProperty("server_time") final long serverTime,
      @JsonProperty("pairs") final Map<String, LiquiPairInfo> pairInfo,
      @JsonProperty("error") final String error) {
    super(pairInfo, error);
    this.serverTime = serverTime;
  }

  public long getServerTime() {
    return serverTime;
  }
}
