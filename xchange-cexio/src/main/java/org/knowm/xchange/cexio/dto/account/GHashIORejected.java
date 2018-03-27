package org.knowm.xchange.cexio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Author: veken0m */
public class GHashIORejected {

  private final BigDecimal stale;
  private final BigDecimal duplicate;
  private final BigDecimal lowdiff;

  /**
   * @param stale
   * @param duplicate
   * @param lowdiff
   */
  public GHashIORejected(
      @JsonProperty("stale") BigDecimal stale,
      @JsonProperty("duplicate") BigDecimal duplicate,
      @JsonProperty("lowdiff") BigDecimal lowdiff) {

    this.stale = stale;
    this.duplicate = duplicate;
    this.lowdiff = lowdiff;
  }

  public BigDecimal getStale() {

    return stale;
  }

  public BigDecimal getDuplicate() {

    return duplicate;
  }

  public BigDecimal getLowdiff() {

    return lowdiff;
  }
}
