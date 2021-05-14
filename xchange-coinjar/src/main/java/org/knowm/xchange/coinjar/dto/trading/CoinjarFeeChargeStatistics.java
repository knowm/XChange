package org.knowm.xchange.coinjar.dto.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;

public class CoinjarFeeChargeStatistics {
  public final Map<String, BigDecimal> volume30d;

  public CoinjarFeeChargeStatistics(@JsonProperty("volume_30d") Map<String, BigDecimal> volume30d) {
    this.volume30d = volume30d;
  }
}
