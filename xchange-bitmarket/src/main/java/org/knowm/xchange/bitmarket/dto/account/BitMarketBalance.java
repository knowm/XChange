package org.knowm.xchange.bitmarket.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;

/** @author kfonal */
public class BitMarketBalance {
  private final Map<String, BigDecimal> available;
  private final Map<String, BigDecimal> blocked;

  public BitMarketBalance(
      @JsonProperty("available") Map<String, BigDecimal> available,
      @JsonProperty("blocked") Map<String, BigDecimal> blocked) {

    this.available = available;
    this.blocked = blocked;
  }

  public Map<String, BigDecimal> getAvailable() {

    return available;
  }

  public Map<String, BigDecimal> getBlocked() {

    return blocked;
  }
}
