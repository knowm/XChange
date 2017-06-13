package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXFeesResult {
  private final Map<String, BigDecimal> progressiveCommissions;

  public DSXFeesResult(@JsonProperty("progressiveCommissions") Map<String, BigDecimal> progressiveCommissions) {

    this.progressiveCommissions = progressiveCommissions;
  }

  public Map<String, BigDecimal> getProgressiveCommissions() {

    return progressiveCommissions;
  }

  @Override
  public String toString() {
    return "DSXCancelAllOrdersResult{" +
        "progressiveCommissions=" + progressiveCommissions +
        '}';
  }
}
