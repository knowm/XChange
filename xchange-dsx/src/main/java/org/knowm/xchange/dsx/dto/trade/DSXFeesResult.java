package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXFeesResult {
  private final DSXProgressiveCommissions progressiveCommissions;

  public DSXFeesResult(@JsonProperty("progressiveCommissions") DSXProgressiveCommissions progressiveCommissions) {

    this.progressiveCommissions = progressiveCommissions;
  }

  public DSXProgressiveCommissions getProgressiveCommissions() {

    return progressiveCommissions;
  }

  @Override
  public String toString() {
    return "DSXCancelAllOrdersResult{" +
        "progressiveCommissions=" + progressiveCommissions +
        '}';
  }
}
